package com.biopark.cpa.controllers.grupos;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.biopark.cpa.controllers.grupos.dto.GenericDTO;
import com.biopark.cpa.entities.grupos.Turma;
import com.biopark.cpa.repository.grupo.TurmaRepository;
import com.biopark.cpa.services.grupos.TurmaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/turma")
@RequiredArgsConstructor

public class TurmaController {

    private final TurmaRepository turmaRepository;
    private final TurmaService turmaService;

    @GetMapping
    public ResponseEntity<Optional<Turma>> buscarCodigoTurma(@RequestParam(name = "codigoTurma") String codigoTurma) {
        var turma = turmaRepository.findByCodigoTurma(codigoTurma);
        if (turma == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(turma);
        }
        return ResponseEntity.status(HttpStatus.OK).body(turma); 
    }

    @GetMapping("/turmas")
    public ResponseEntity<List<Turma>>buscarTodasTurmas() {
        var turmas = turmaRepository.findAll();
        if (turmas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(turmas);
        }
        return ResponseEntity.status(HttpStatus.OK).body(turmas);
    }
    
    @PutMapping
    public ResponseEntity<GenericDTO> editarTurma (@RequestBody Turma turma) {
        GenericDTO response = turmaService.editarTurma(turma);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<GenericDTO> excluirTurma(@RequestParam("id") int idRequest) {
        Long id = Long.valueOf(idRequest);
        GenericDTO response = turmaService.excluirTurma(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}