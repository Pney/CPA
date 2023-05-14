package com.biopark.cpa.services.grupos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.dto.GenericDTO;
import com.biopark.cpa.dto.cadastroCsv.CadastroDTO;
import com.biopark.cpa.dto.cadastroCsv.ErroValidation;
import com.biopark.cpa.dto.cadastroCsv.ValidationModel;
import com.biopark.cpa.entities.grupos.Curso;
import com.biopark.cpa.repository.grupo.CursoRepository;
import com.biopark.cpa.repository.grupo.InstituicaoRepository;
import com.biopark.cpa.services.utils.CsvParserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class CursoService {
    private final CursoRepository cursoRepository;
    private final CsvParserService csvParserService;
    private final InstituicaoRepository instituicaoRepository;

    @Transactional
    public CadastroDTO cadastrarCurso(List<Curso> cursos, boolean update){
        List<ErroValidation> errors = csvParserService.validaEntrada(cursos);
        List<ErroValidation> warnings = new ArrayList<>();

        if (!errors.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.BAD_REQUEST).erros(errors).warnings(warnings).build();
        }

        ValidationModel<Curso> model = verificaInstituicao(cursos);
        List<ErroValidation> naoExiste = model.getErrors();
        cursos = model.getObjects();

        if (!naoExiste.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.NOT_FOUND).erros(naoExiste).warnings(warnings).build();
        }

        if (!update) {
            model = checarDuplicatas(cursos);
            List<ErroValidation> duplicatas = model.getErrors();
            warnings = model.getWarnings();
            cursos = model.getObjects();
    
            if (!duplicatas.isEmpty()) {
                return CadastroDTO.builder().status(HttpStatus.CONFLICT).erros(duplicatas).warnings(warnings).build();
            }      

            cursoRepository.saveAll(cursos);
            return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
        }

        for (Curso curso : cursos) {
            cursoRepository.upsert(curso);
        }
        
        return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
    }

    private ValidationModel<Curso> checarDuplicatas(List<Curso> cursos) {
        List<ErroValidation> erroValidations = new ArrayList<>();
        List<ErroValidation> warnings = new ArrayList<>();
        List<Curso> unicosCod = new ArrayList<>();
        List<Curso> unicosNome = new ArrayList<>();

        Map<String, Integer> uniqueCod = new HashMap<String, Integer>();
        Map<String, Integer> uniqueNome = new HashMap<String, Integer>();

        int linha = 0;
        for (Curso curso: cursos) {
            linha ++;
            if (!uniqueCod.containsKey(curso.getCodCurso())) {
                uniqueCod.put(curso.getCodCurso(), linha);
                unicosCod.add(curso);
            }else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o código já existe na linha: "
                                + uniqueCod.get(curso.getCodCurso()))
                        .build());
                continue;
            }

            if(!uniqueNome.containsKey(curso.getNomeCurso())){
                uniqueNome.put(curso.getNomeCurso(), linha);
                unicosNome.add(curso);
            }else{
                warnings.add(ErroValidation.builder()
                    .linha(linha)
                    .mensagem("Esta linha foi ignorada pois o nome já existe na linha: "
                        + uniqueNome.get(curso.getNomeCurso()))
                    .build());
                continue;
            }

            if (cursoRepository.findByCodCurso(curso.getCodCurso()).isPresent() || cursoRepository.findByNomeCurso(curso.getNomeCurso()).isPresent()) {
                erroValidations.add(ErroValidation.builder().linha(linha).mensagem("Curso já cadastrado").build());
            }            
        }

        List<Curso> unicos = unicosNome;
        unicos.retainAll(unicosCod);

        return ValidationModel.<Curso>builder().errors(erroValidations).warnings(warnings).objects(unicos)
                .build();
    }   

    private ValidationModel<Curso> verificaInstituicao(List<Curso> cursos){
        List<ErroValidation> erros = new ArrayList<>();

        int linha = 0;
        for (Curso curso: cursos) {
            linha ++;
            var instituicaoFind = instituicaoRepository.findByCodigoInstituicao(curso.getCodInstituicao().toLowerCase()); 
            if (!instituicaoFind.isPresent()) {
                erros.add(  
                    ErroValidation.builder()
                        .linha(linha)
                        .mensagem("A instituição ligada a este curso não está cadastrada")
                        .build()
                );
            }else{
                curso.setInstituicao(instituicaoFind.get());
            }
        }
        return ValidationModel.<Curso>builder().errors(erros).objects(cursos).build();
    }

    // Filtrar Curso por ID
    public Curso buscarPorCodigo(String codigo) {
        var optionalCurso = cursoRepository.findByCodCurso(codigo);
        if (optionalCurso.isPresent()) {
            return optionalCurso.get();
        } else {
            throw new RuntimeException("Curso não encontrado!");
        }
    }

    public List<Curso> buscarTodosCursos() {
        var cursos = cursoRepository.findAll();
        if (cursos.isEmpty()) {
        throw new RuntimeException("Não há cursos cadastrados!");
        }
        return cursos;
        }

    // Editar Curso por ID
    public GenericDTO editarCurso(Curso cursoRequest) {
        try {
            Curso curso = buscarPorCodigo(cursoRequest.getCodigoCurso());
            // esse set vai ser para quando tiver a coluna Nome Curso no banco.
            // curso.setNomeCurso(cursoRequest.getNomeCurso());

            cursoRepository.save(curso);
            return GenericDTO.builder().status(HttpStatus.OK)
                    .mensagem("Curso " + cursoRequest.getCodigoCurso() + " editado com sucesso")
                    .build();
        } catch (Exception e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem(e.getMessage()).build();
        }
    }

    // Excluir Curso
    public GenericDTO excluirCurso(Long id) {
        try {
            var cursoDB = cursoRepository.findById(id);
            if (!cursoDB.isPresent()) {
                return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem("curso não encontrado").build();
            }
            Curso curso = cursoDB.get();
            cursoRepository.delete(curso);
            return GenericDTO.builder().status(HttpStatus.OK)
                    // Está sendo passando o get pelo codigo curso, pois, não tem a coluna nome ainda no banco.
                    .mensagem("Curso " + curso.getCodigoCurso() + " excluído com sucesso")
                    .build();
        } catch (EmptyResultDataAccessException e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND)
                    .mensagem("Curso " + id + " não encontrada")
                    .build();
        }
    }
}
