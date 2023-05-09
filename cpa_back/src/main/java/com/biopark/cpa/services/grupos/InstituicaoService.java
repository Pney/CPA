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
import com.biopark.cpa.entities.grupos.Instituicao;
import com.biopark.cpa.repository.grupo.InstituicaoRepository;
import com.biopark.cpa.services.utils.CsvParserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InstituicaoService {
    private final CsvParserService csvParserService;
    private final InstituicaoRepository instituicaoRepository;

    @Transactional
    public CadastroDTO cadastrarInstituicao(List<Instituicao> instituicoes, boolean update) {
        List<ErroValidation> errors = csvParserService.validaEntrada(instituicoes);
        List<ErroValidation> warnings = new ArrayList<>();

        if (!errors.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.BAD_REQUEST).erros(errors).warnings(warnings).build();
        }

        if (!update) {
            ValidationModel<Instituicao> model = checarDuplicatas(instituicoes);
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

    private ValidationModel<Instituicao> checarDuplicatas(List<Instituicao> instituicoes) {
        List<ErroValidation> erroValidations = new ArrayList<>();
        List<ErroValidation> warnings = new ArrayList<>();
        List<Instituicao> unicos = new ArrayList<>();

        Map<String, Integer> uniqueCod = new HashMap<String, Integer>();

        int linha = 0;
        for (Instituicao instituicao: instituicoes) {
            linha ++;
            if (!uniqueCod.containsKey(instituicao.getCodigoInstituicao())) {
                uniqueCod.put(instituicao.getCodigoInstituicao(), linha);
                unicos.add(instituicao);
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o código já existe na linha: "
                                + uniqueCod.get(instituicao.getCodigoInstituicao()))
                        .build());
                continue;
            }

            if (instituicaoRepository.findByCodigoInstituicao(instituicao.getCodigoInstituicao().toLowerCase()).isPresent()) {
                erroValidations
                        .add(ErroValidation.builder().linha(linha).mensagem("Instituição já cadastrada").build());
            }
        }

        return ValidationModel.<Instituicao>builder().errors(erroValidations).warnings(warnings).objects(unicos)
                .build();
    }
}
