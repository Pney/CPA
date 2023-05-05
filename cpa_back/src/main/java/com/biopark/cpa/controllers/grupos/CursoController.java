package com.biopark.cpa.controllers.grupos;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biopark.cpa.dto.cadastroCsv.CadastroDTO;
import com.biopark.cpa.entities.grupos.Curso;
import com.biopark.cpa.repository.grupo.CursoRepository;
import com.biopark.cpa.services.grupos.CursoService;
import com.biopark.cpa.services.utils.CsvParserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/curso")
@RequiredArgsConstructor

public class CursoController {
    private final CursoRepository cursoRepository;
    private final CursoService cursoService;
    private final CsvParserService csvParserService;

    @PostMapping
    public ResponseEntity<CadastroDTO> cadastrarCurso(@RequestParam("file") MultipartFile file) throws IOException{
        List<Curso> cursos = csvParserService.parseCsv(file, Curso.class);
        CadastroDTO cadastroDTO = cursoService.cadastrarCurso(cursos);
        return ResponseEntity.status(cadastroDTO.getStatus()).body(cadastroDTO);
    }

    @GetMapping
    public ResponseEntity<Optional<Curso>> buscarCodigoCurso(@RequestParam(name = "codCurso") String codigoCurso) {
        var curso = cursoRepository.findByCodCurso(codigoCurso);
        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(curso);
        }
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }
}