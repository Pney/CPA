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
import com.biopark.cpa.entities.grupos.Curso;
import com.biopark.cpa.repository.grupo.CursoRepository;
import com.biopark.cpa.repository.grupo.InstituicaoRepository;
import com.biopark.cpa.services.utils.CsvParserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CursoService {
    private final CsvParserService csvParserService;
    private final CursoRepository cursoRepository;
    private final InstituicaoRepository instituicaoRepository;

    @Transactional
    public CadastroDTO cadastrarCurso(List<Curso> cursos){
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

    private ValidationModel<Curso> checarDuplicatas(List<Curso> cursos) {
        List<ErroValidation> erroValidations = new ArrayList<>();
        List<ErroValidation> warnings = new ArrayList<>();
        List<Curso> unicosCod = new ArrayList<>();
        List<Curso> unicosNome = new ArrayList<>();

        Map<String, Integer> uniqueCod = new HashMap<String, Integer>();
        Map<String, Integer> uniqueNome = new HashMap<String, Integer>();

        for (int i = 0; i < cursos.size(); i++) {

            if (!uniqueCod.containsKey(cursos.get(i).getCodCurso())) {
                uniqueCod.put(cursos.get(i).getCodCurso(), i + 1);
                unicosCod.add(cursos.get(i));
            }else {
                warnings.add(ErroValidation.builder()
                        .linha(i + 1)
                        .mensagem("Esta linha foi ignorada pois o código já existe na linha: "
                                + uniqueCod.get(cursos.get(i).getCodCurso()))
                        .build());
                continue;
            }

            if(!uniqueNome.containsKey(cursos.get(i).getNomeCurso())){
                uniqueNome.put(cursos.get(i).getNomeCurso(), i + 1);
                unicosNome.add(cursos.get(i));
            }else{
                warnings.add(ErroValidation.builder()
                    .linha(i + 1)
                    .mensagem("Esta linha foi ignorada pois o nome já existe na linha: "
                        + uniqueNome.get(cursos.get(i).getNomeCurso()))
                    .build());
                continue;
            }

            if (cursoRepository.findByCodCurso(cursos.get(i).getCodCurso()).isPresent() || cursoRepository.findByNomeCurso(cursos.get(i).getNomeCurso()).isPresent()) {
                erroValidations.add(ErroValidation.builder().linha(i + 1).mensagem("Curso já cadastrado").build());
            }            
        }

        List<Curso> unicos = unicosNome;
        unicos.retainAll(unicosCod);

        return ValidationModel.<Curso>builder().errors(erroValidations).warnings(warnings).objects(unicos)
                .build();
    }   

    private ValidationModel<Curso> verificaInstituicao(List<Curso> cursos){
        List<ErroValidation> erros = new ArrayList<>();
    
        for (int i = 0; i < cursos.size(); i++) {
            var instituicaoFind = instituicaoRepository.findByCodigoInstituicao(cursos.get(i).getCodInstituicao()); 
            if (!instituicaoFind.isPresent()) {
                erros.add(  
                    ErroValidation.builder()
                        .linha(i+1)
                        .mensagem("A instituição ligada a este curso não está cadastrada")
                        .build()
                );
            }else{
                cursos.get(i).setInstituicao(instituicaoFind.get());
            }
        }
        return ValidationModel.<Curso>builder().errors(erros).objects(cursos).build();
    }
}
