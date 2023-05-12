package com.biopark.cpa.controllers.grupos;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biopark.cpa.controllers.grupos.dto.CadastroDTO;
import com.biopark.cpa.controllers.grupos.dto.GenericDTO;
import com.biopark.cpa.entities.grupos.Desafio;
import com.biopark.cpa.repository.grupo.DesafioRepository;
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
    private final DesafioRepository desafioRepository;

    //Cadastrar um Desafio
    @PostMapping
    public ResponseEntity<CadastroDTO> cadastrarDesafio(@RequestParam("file") MultipartFile file) throws IOException {
        List<Desafio> desafios = csvParserService.parseCsv(file, Desafio.class);
        CadastroDTO cadastroDTO = desafioService.cadastrarDesafio(desafios);
        return ResponseEntity.status(cadastroDTO.getStatus()).body(cadastroDTO);
    }

    //Buscar apenas um desafio apssando o nomeDesafio como paramêtro.
    @GetMapping
    public ResponseEntity<Optional<Desafio>> buscarNomeDesafio(@RequestParam(name = "nomeDesafio") String nomeDesafio) {
        nomeDesafio = nomeDesafio.toLowerCase();
        var desafio = desafioRepository.findByNomeDesafio(nomeDesafio);
        if (desafio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(desafio);
        }
        return ResponseEntity.status(HttpStatus.OK).body(desafio);
    }

    //Buscar varios desafios passando o caminho api/desafio/desafios
    @GetMapping("/desafios")
    public ResponseEntity<List<Desafio>> buscarTodosDesafios() {
        List<Desafio> desafios = desafioRepository.findAll();
        if (desafios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(desafios);
    }

    //Editar Desafio
    @PutMapping
    public ResponseEntity<GenericDTO> editarDesafio(@RequestBody Desafio desafio) {
        GenericDTO response = desafioService.editarDesafio(desafio);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    //Deletar um Desafio
    @DeleteMapping
    public ResponseEntity<GenericDTO> excluirDesafio(@PathVariable Long id) {
        GenericDTO response = desafioService.excluirDesafio(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}