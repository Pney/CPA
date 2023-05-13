package com.biopark.cpa.dto.cadastroCsv;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CadastroDTO {
    private HttpStatus status;
    private List<ErroValidation> erros;
    private List<ErroValidation> warnings;
}
