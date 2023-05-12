package com.biopark.cpa.services.grupos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.dto.cadastroCsv.CadastroDTO;
import com.biopark.cpa.dto.cadastroCsv.ErroValidation;
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
    private final CsvParserService csvParserService;
    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;

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
        for (Turma turma: turmas) {
            linha ++;
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

    private ValidationModel<Turma> verificaCurso(List<Turma> turmas){
        List<ErroValidation> erros = new ArrayList<>();
        
        int linha = 0;
        for (Turma turma: turmas) {
            linha++;
            var cursoFind = cursoRepository.findByCodCurso(turma.getCodCurso().toLowerCase()); 
            if (!cursoFind.isPresent()) {
                erros.add(  
                    ErroValidation.builder()
                        .linha(linha)
                        .mensagem("A instituição ligada a este curso não está cadastrada")
                        .build()
                );
            }else{
                turma.setCurso(cursoFind.get());
            }
        }
        return ValidationModel.<Turma>builder().errors(erros).objects(turmas).build();
    }
}
