package com.biopark.cpa.services.grupos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.controllers.grupos.dto.CadastroDTO;
import com.biopark.cpa.controllers.grupos.dto.ErroValidation;
import com.biopark.cpa.entities.grupos.Instituicao;
import com.biopark.cpa.repository.grupo.InstituicaoRepository;
import com.biopark.cpa.services.CsvParserService;
import com.biopark.cpa.services.responses.DuplicatesModel;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InstituicaoService {
    @Autowired
    private CsvParserService csvParserService;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Transactional
    public CadastroDTO cadastrarInstituicao(List<Instituicao> instituicoes, boolean update) {
        List<ErroValidation> errors = csvParserService.validaEntrada(instituicoes);
        List<ErroValidation> warnings = new ArrayList<>();

        if (!errors.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.BAD_REQUEST).erros(errors).warnings(warnings).build();
        }

        if (!update) {
            DuplicatesModel<Instituicao> model = checarDuplicatas(instituicoes);
            List<ErroValidation>duplicatas = model.getErrors();
            warnings = model.getWarnings();
            
            instituicoes = model.getObjects();

            if (!duplicatas.isEmpty()) {
                return CadastroDTO.builder().status(HttpStatus.CONFLICT).erros(duplicatas).warnings(warnings).build();
            }

            instituicaoRepository.saveAll(instituicoes);
            return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
        }

        for (Instituicao instituicao : instituicoes) {
            instituicaoRepository.upsert(instituicao);
        }

        return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
    }

    private DuplicatesModel<Instituicao> checarDuplicatas(List<Instituicao> instituicoes) {
        List<ErroValidation> erroValidations = new ArrayList<>();
        List<ErroValidation> warnings = new ArrayList<>();
        List<Instituicao> unicos = new ArrayList<>();
      
        Set<String> uniqueCod = new HashSet<>();
      
        for (int i = 0; i < instituicoes.size(); i++) {

            if (!uniqueCod.contains(instituicoes.get(i).getCodigoInstituicao())) {
                uniqueCod.add(instituicoes.get(i).getCodigoInstituicao());
                unicos.add(instituicoes.get(i));
            }else{
                warnings.add(ErroValidation.builder().linha(i+1).mensagem("Esta linha foi ignorada pois o código já existe no arquivo enviado").build());
                continue;
            }
            
            if (instituicaoRepository.findByCodigoInstituicao(instituicoes.get(i).getCodigoInstituicao()).isPresent()) {
                erroValidations
                        .add(ErroValidation.builder().linha(i + 1).mensagem("Instituição já cadastrada").build());
            }
        }

        return DuplicatesModel.<Instituicao>builder().errors(erroValidations).warnings(warnings).objects(unicos).build();
    }
}
