package com.biopark.cpa.services.grupos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.controllers.grupos.dto.CadastroDTO;
import com.biopark.cpa.controllers.grupos.dto.EditarDTO;
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
            List<ErroValidation> duplicatas = model.getErrors();
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

        Map<String, Integer> uniqueCod = new HashMap<String, Integer>();

        for (int i = 0; i < instituicoes.size(); i++) {

            if (!uniqueCod.containsKey(instituicoes.get(i).getCodigoInstituicao())) {
                uniqueCod.put(instituicoes.get(i).getCodigoInstituicao(), i + 1);
                unicos.add(instituicoes.get(i));
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(i + 1)
                        .mensagem("Esta linha foi ignorada pois o código já existe na linha: "
                                + uniqueCod.get(instituicoes.get(i).getCodigoInstituicao()))
                        .build());
                continue;
            }

            if (instituicaoRepository.findByCodigoInstituicao(instituicoes.get(i).getCodigoInstituicao()).isPresent()) {
                erroValidations
                        .add(ErroValidation.builder().linha(i + 1).mensagem("Instituição já cadastrada").build());
            }
        }

        return DuplicatesModel.<Instituicao>builder().errors(erroValidations).warnings(warnings).objects(unicos)
                .build();
    }

    public Instituicao buscarPorCodigo(String codigo) {
        var optionalInstituicao = instituicaoRepository.findByCodigoInstituicao(codigo);

        if (optionalInstituicao.isPresent()) {
            return optionalInstituicao.get();
        } else {
            throw new RuntimeException("Instituição não encontrado!");
        }
    }

    public EditarDTO editarInstituicao(Instituicao instituicaoRequest) {
        try {
            Instituicao instituicao = buscarPorCodigo(instituicaoRequest.getCodigoInstituicao());
            instituicao.setNomeInstituicao(instituicaoRequest.getNomeInstituicao());
            instituicao.setEmail(instituicaoRequest.getEmail());
            instituicaoRepository.save(instituicao);
            return EditarDTO.builder().status(HttpStatus.OK)
                    .mensagem("Instituicao " + instituicaoRequest.getCodigoInstituicao() + " editado com sucesso")
                    .build();
        } catch (Exception e) {
            return EditarDTO.builder().status(HttpStatus.NOT_FOUND).mensagem(e.getMessage()).build();
        }
    }


    public EditarDTO excluirInstituicao (Long id) {
        // Long id = Long.valueOf(idInt);
        try {
          
            
            // Instituicao instituicao = buscarPorCodigo(id);
            var instituicaoDB = instituicaoRepository.findById(id);
            if (!instituicaoDB.isPresent()) {
                return EditarDTO.builder().status(HttpStatus.NOT_FOUND).mensagem("instituição não encontrada").build();
            }

            Instituicao instituicao = instituicaoDB.get();

            instituicaoRepository.delete(instituicao);
            return EditarDTO.builder().status(HttpStatus.OK)
                    .mensagem("Instituicao " + instituicao.getNomeInstituicao() + " excluída com sucesso")
                    .build();
        } catch (EmptyResultDataAccessException e) {
            return EditarDTO.builder().status(HttpStatus.NOT_FOUND)
                    .mensagem("Instituicao " + id + " não encontrada")
                    .build();
        }
    }
}
