
package com.biopark.cpa.controllers.grupos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.cpa.entities.grupos.Curso;
import com.biopark.cpa.repository.grupo.CursoRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/curso")
@RequiredArgsConstructor

public class CursoController {

    private final CursoRepository cursoRepository;

    @GetMapping
    public ResponseEntity<Optional<Curso>> buscarCodigoCurso(@RequestParam(name = "codigoCurso") String codigoCurso) {
        var curso = cursoRepository.findByCodigoCurso(codigoCurso);
        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(curso);
        }
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }
}