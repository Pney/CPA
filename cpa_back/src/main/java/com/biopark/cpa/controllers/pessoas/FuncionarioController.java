package com.biopark.cpa.controllers.pessoas;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biopark.cpa.dto.cadastroCsv.CadastroDTO;
import com.biopark.cpa.form.cadastroCsv.FuncionarioModel;
import com.biopark.cpa.services.pessoas.FuncionarioService;
import com.biopark.cpa.services.utils.CsvParserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/funcionario")
public class FuncionarioController {
    private final CsvParserService csvParserService;
    private final FuncionarioService funcionarioService;

    @PostMapping
    @PreAuthorize("hasRole('CPA')")
    public ResponseEntity<CadastroDTO> cadastrarFuncionario(@RequestParam("file") MultipartFile file, 
            @RequestParam("update") boolean update) throws IOException {
        List<FuncionarioModel> funcionarios = csvParserService.parseCsv(file, FuncionarioModel.class);
        CadastroDTO cadastroDTO = funcionarioService.cadastrarFuncionario(funcionarios, update);
        return ResponseEntity.status(cadastroDTO.getStatus()).body(cadastroDTO);
    }
}
