package com.biopark.cpa.controllers.cadastros;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.cpa.controllers.cadastros.request.CadastroCpa;
import com.biopark.cpa.controllers.cadastros.response.ResponseMensagem;
import com.biopark.cpa.services.MembrosCPAService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class CadastrosController {
    private final MembrosCPAService membrosCPAService;

    @PostMapping("public/membros-cpa")
    public ResponseEntity<ResponseMensagem> cadastroCPA(@RequestBody CadastroCpa membrosCPA) {
        return ResponseEntity.ok(membrosCPAService.cadastrarCPA(membrosCPA));
    }
}
