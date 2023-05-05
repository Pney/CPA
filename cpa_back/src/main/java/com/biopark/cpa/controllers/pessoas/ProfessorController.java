// package com.biopark.cpa.controllers.pessoas;

// import java.io.IOException;
// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.biopark.cpa.dto.cadastroCsv.CadastroDTO;
// import com.biopark.cpa.form.cadastroCsv.ProfessorModel;
// import com.biopark.cpa.services.pessoas.ProfessorService;
// import com.biopark.cpa.services.utils.CsvParserService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequiredArgsConstructor
// @RequestMapping("api/professor")
// public class ProfessorController {

//     private final CsvParserService csvParserService;
//     private final ProfessorService professorService;

//     @PostMapping
//     public ResponseEntity<CadastroDTO> cadastrarProfessor(@RequestParam("file") MultipartFile file,
//             @RequestParam("update") Boolean update) throws IOException {
//         List<ProfessorModel> professores = csvParserService.parseCsv(file, ProfessorModel.class);
//         CadastroDTO cadastroDTO = professorService.cadastrarProfessor(professores, update);
//         return ResponseEntity.status(cadastroDTO.getStatus()).body(cadastroDTO);
//     }
// }
