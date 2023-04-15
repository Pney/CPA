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
import com.biopark.cpa.entities.grupos.Desafio;
import com.biopark.cpa.services.CsvParserService;
import com.biopark.cpa.services.grupos.DesafioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/desafio")
@RequiredArgsConstructor
public class DesafioController {
    
    @Autowired
    private final CsvParserService csvParserService;
    private final DesafioService desafioService;

    @PostMapping
    public ResponseEntity<CadastroDTO> cadastrarDesafio(@RequestParam("file") MultipartFile file) throws IOException{
        List<Desafio> desafios = csvParserService.parseCsv(file, Desafio.class);
        CadastroDTO cadastroDTO = desafioService.cadastrarDesafio(desafios);
        return ResponseEntity.status(cadastroDTO.getStatus()).body(cadastroDTO);
    }
}
