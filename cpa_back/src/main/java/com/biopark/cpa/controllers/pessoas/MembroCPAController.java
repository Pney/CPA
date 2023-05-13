package com.biopark.cpa.controllers.pessoas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.cpa.dto.GenericDTO;
import com.biopark.cpa.form.pessoas.CadastroCPA;
import com.biopark.cpa.services.pessoas.MembrosCPAService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/membros-cpa")
@RequiredArgsConstructor
public class MembroCPAController {
    private final MembrosCPAService membrosCPAService;

    @PostMapping
    public ResponseEntity<GenericDTO> cadastrarMembroExterno(@RequestBody CadastroCPA membroCPA){
        GenericDTO response = membrosCPAService.cadastrarCPA(membroCPA);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
