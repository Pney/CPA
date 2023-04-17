package com.biopark.cpa.controllers.grupos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.cpa.entities.grupos.Turma;
import com.biopark.cpa.repository.grupo.TurmaRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/turma")
@RequiredArgsConstructor

public class TurmaController {
    
    @Autowired
    private final TurmaRepository turmaRepository;

    @GetMapping
    public ResponseEntity<Optional<Turma>> buscarCodigoTurma(@RequestParam(name = "codigoTurma") String codigoTurma) {
        var turma = turmaRepository.findByCodigoTurma(codigoTurma);
        if (turma == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(turma);
        }
        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }
}
