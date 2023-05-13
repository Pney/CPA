package com.biopark.cpa.services.grupos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.dto.cadastroCsv.CadastroDTO;
import com.biopark.cpa.dto.cadastroCsv.ErroValidation;
import com.biopark.cpa.dto.cadastroCsv.GenericDTO;
import com.biopark.cpa.dto.cadastroCsv.ValidationModel;
import com.biopark.cpa.entities.grupos.Turma;
import com.biopark.cpa.repository.grupo.CursoRepository;
import com.biopark.cpa.repository.grupo.TurmaRepository;
import com.biopark.cpa.services.utils.CsvParserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TurmaService {
    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;
    private final CsvParserService csvParserService;

    @Transactional
    public CadastroDTO cadastrarTurma(List<Turma> turmas, boolean update){
        List<ErroValidation> errors = csvParserService.validaEntrada(turmas);
        List<ErroValidation> warnings = new ArrayList<>();

        if (!errors.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.BAD_REQUEST).erros(errors).warnings(warnings).build();
        }

        ValidationModel<Turma> model = verificaCurso(turmas);
        List<ErroValidation> naoExiste = model.getErrors();
        turmas = model.getObjects();

        if (!naoExiste.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.NOT_FOUND).erros(naoExiste).warnings(warnings).build();
        }

        if (!update) {
            model = checarDuplicatas(turmas);
            List<ErroValidation> duplicatas = model.getErrors();
            warnings = model.getWarnings();

            turmas = model.getObjects();

            if (!duplicatas.isEmpty()) {
                return CadastroDTO.builder().status(HttpStatus.CONFLICT).erros(duplicatas).warnings(warnings).build();
            }

            turmaRepository.saveAll(turmas);
            return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
        }

        for (Turma turma : turmas) {
            turmaRepository.upsert(turma);
        }

        return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
    }

    private ValidationModel<Turma> checarDuplicatas(List<Turma> turmas) {
        List<ErroValidation> erroValidations = new ArrayList<>();
        List<ErroValidation> warnings = new ArrayList<>();
        List<Turma> unicos = new ArrayList<>();

        Map<String, Integer> uniqueCod = new HashMap<String, Integer>();

        int linha = 0;
        for (Turma turma : turmas) {
            linha++;
            if (!uniqueCod.containsKey(turma.getCodTurma())) {
                uniqueCod.put(turma.getCodTurma(), linha);
                unicos.add(turma);
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o código já existe na linha: "
                                + uniqueCod.get(turma.getCodTurma()))
                        .build());
                continue;
            }

            if (turmaRepository.findByCodTurma(turma.getCodTurma().toLowerCase()).isPresent()) {
                erroValidations
                        .add(ErroValidation.builder().linha(linha).mensagem("Turma já cadastrada").build());
            }
        }

        return ValidationModel.<Turma>builder().errors(erroValidations).warnings(warnings).objects(unicos)
                .build();
    }

    private ValidationModel<Turma> verificaCurso(List<Turma> turmas) {
        List<ErroValidation> erros = new ArrayList<>();

        int linha = 0;
        for (Turma turma : turmas) {
            linha++;
            var cursoFind = cursoRepository.findByCodCurso(turma.getCodCurso().toLowerCase());
            if (!cursoFind.isPresent()) {
                erros.add(
                        ErroValidation.builder()
                                .linha(linha)
                                .mensagem("A instituição ligada a este curso não está cadastrada")
                                .build());
            } else {
                turma.setCurso(cursoFind.get());
            }
        }
        return ValidationModel.<Turma>builder().errors(erros).objects(turmas).build();
    }

    // Filtrar Turma por ID
    public Turma buscarPorCodigo(String codigo) {
        var optionalTurma = turmaRepository.findByCodTurma(codigo);

        if (optionalTurma.isPresent()) {
            return optionalTurma.get();
        } else {
            throw new RuntimeException("Turma não encontrada!");
        }
    }

    public List<Turma> buscarTodasTurmas() {
        var turmas = turmaRepository.findAll();
        if (turmas.isEmpty()) {
            throw new RuntimeException("Não há turmas cadastradas!");
        }
        return turmas;
    }

    // Editar Turma por ID
    public GenericDTO editarTurma(Turma turmaRequest) {
        try {
            Turma turma = buscarPorCodigo(turmaRequest.getCodTurma());
            // esse set vai ser para quando tiver a coluna Nome Curso no banco.
            // turma.setNomeTurma(turmaRequest.getNomeTurma());

            turmaRepository.save(turma);
            return GenericDTO.builder().status(HttpStatus.OK)
                    .mensagem("Turma " + turmaRequest.getCodTurma() + " editado com sucesso")
                    .build();
        } catch (Exception e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem(e.getMessage()).build();
        }
    }

    // Excluir Turma
    public GenericDTO excluirTurma(Long id) {
        try {
            var turmaDB = turmaRepository.findById(id);
            if (!turmaDB.isPresent()) {
                return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem("turma não encontrada").build();
            }
            Turma turma = turmaDB.get();
            turmaRepository.delete(turma);
            return GenericDTO.builder().status(HttpStatus.OK)
                    // Está sendo passando o get pelo codigo turma, pois, não tem a coluna nome
                    // ainda no banco.
                    .mensagem("Turma " + turma.getCodTurma() + " excluída com sucesso")
                    .build();
        } catch (EmptyResultDataAccessException e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND)
                    .mensagem("Turma " + id + " não encontrada")
                    .build();
        }
    }
}
