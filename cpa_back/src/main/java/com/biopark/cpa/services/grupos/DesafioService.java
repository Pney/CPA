package com.biopark.cpa.services.grupos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.controllers.grupos.dto.CadastroDTO;
import com.biopark.cpa.controllers.grupos.dto.ErroValidation;
import com.biopark.cpa.entities.grupos.Desafio;
import com.biopark.cpa.repository.grupo.DesafioRepository;
import com.biopark.cpa.services.CsvParserService;
import com.biopark.cpa.services.responses.DuplicatesModel;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DesafioService {
    @Autowired
    private CsvParserService csvParserService;

    @Autowired
    private DesafioRepository desafioRepository;

    @Transactional
    public CadastroDTO cadastrarDesafio(List<Desafio> desafios) {
        List<ErroValidation> errors = csvParserService.validaEntrada(desafios);
        List<ErroValidation> warnings = new ArrayList<>();

        if (!errors.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.BAD_REQUEST).erros(errors).warnings(warnings).build();
        }

        DuplicatesModel<Desafio> model = checarDuplicatas(desafios);
        List<ErroValidation> duplicatas = model.getErrors();
        warnings = model.getWarnings();

        desafios = model.getObjects();

        if (!duplicatas.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.CONFLICT).erros(duplicatas).warnings(warnings).build();
        }

        desafioRepository.saveAll(desafios);
        return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
    }

    private DuplicatesModel<Desafio> checarDuplicatas(List<Desafio> desafios) {
        List<ErroValidation> erroValidations = new ArrayList<>();
        List<ErroValidation> warnings = new ArrayList<>();
        List<Desafio> unicos = new ArrayList<>();

        Map<String, Integer> uniqueCod = new HashMap<String, Integer>();

        for (int i = 0; i < desafios.size(); i++) {

            if (!uniqueCod.containsKey(desafios.get(i).getNomeDesafio())) {
                uniqueCod.put(desafios.get(i).getNomeDesafio(), i + 1);
                desafios.get(i).setNomeDesafio(desafios.get(i).getNomeDesafio().toLowerCase());
                unicos.add(desafios.get(i));
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(i + 1)
                        .mensagem("Esta linha foi ignorada pois o código já existe na linha: "
                                + uniqueCod.get(desafios.get(i).getNomeDesafio()))
                        .build());
                continue;
            }

            if (desafioRepository.findByNomeDesafio(desafios.get(i).getNomeDesafio()).isPresent()) {
                erroValidations
                        .add(ErroValidation.builder().linha(i + 1).mensagem("Desafio já cadastrado").build());
            }
        }

        return DuplicatesModel.<Desafio>builder().errors(erroValidations).warnings(warnings).objects(unicos)
                .build();
    }

   
    public Desafio buscarPorId(Long id) {
        var optionalDesafio = desafioRepository.findById(id);

        if (optionalDesafio.isPresent()) {
            return optionalDesafio.get();
        } else {
            throw new RuntimeException("Desafio não encontrado");
        }
    }

    public void atualizarDesafio(Long id, String novoNome) {
        Desafio desafio = buscarPorId(id);
        desafio.setNomeDesafio(novoNome);
        desafioRepository.save(desafio); // Salva as mudanças no banco de dados
    }
}
}
