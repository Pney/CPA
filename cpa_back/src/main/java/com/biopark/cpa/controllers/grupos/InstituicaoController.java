package com.biopark.cpa.controllers.grupos;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biopark.cpa.controllers.grupos.dto.CadastroDTO;
import com.biopark.cpa.entities.grupos.Instituicao;
import com.biopark.cpa.services.CsvParserService;
import com.biopark.cpa.services.grupos.InstituicaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/instituicao")
@RequiredArgsConstructor
public class InstituicaoController {
    
    @Autowired
    private final CsvParserService csvParserService;
    private final InstituicaoService instituicaoService;


    @PostMapping
    public ResponseEntity<CadastroDTO> cadastrarInstituicao(@RequestParam("file") MultipartFile file, @RequestParam(name = "update") Boolean update) throws IOException{
        List<Instituicao> instituicoes = csvParserService.parseCsv(file, Instituicao.class);
        CadastroDTO cadastroDTO = instituicaoService.cadastrarInstituicao(instituicoes, update);
        return ResponseEntity.status(cadastroDTO.getStatus()).body(cadastroDTO);
    }
}
