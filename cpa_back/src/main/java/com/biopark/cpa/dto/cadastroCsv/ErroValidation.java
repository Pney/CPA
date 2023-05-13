package com.biopark.cpa.dto.cadastroCsv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErroValidation {
    private int linha;
    private String mensagem;
}
