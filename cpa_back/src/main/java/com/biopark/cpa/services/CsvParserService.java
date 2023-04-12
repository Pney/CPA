package com.biopark.cpa.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biopark.cpa.controllers.grupos.dto.ErroValidation;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CsvParserService {
    @Autowired
    private Validator validator;

    public <T> List<T> parseCsv(MultipartFile file, Class<T> classe) throws IOException{
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<T>();
        strategy.setType(classe);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new InputStreamReader(file.getInputStream()))
            .withType(classe)
            .withSeparator(';')
            .withMappingStrategy(strategy)
            .withIgnoreLeadingWhiteSpace(true)
            .build();

        return csvToBean.parse();
    }

    public <T> List<ErroValidation> validaEntrada(List<T> data){
        List<ErroValidation> erro = new ArrayList<>();
        
        for (int index = 0; index < data.size(); index++) {
            Set<ConstraintViolation<T>> violacoes = validator.validate(data.get(index));

            if (!violacoes.isEmpty()) {
                String mensagem = "";
                for (ConstraintViolation<T> violacao : violacoes) {
                    mensagem += violacao.getMessage()+"; ";
                }
                erro.add(ErroValidation.builder().linha(index+1).mensagem(mensagem).build());
            }
        }

        return erro;
    }
}
