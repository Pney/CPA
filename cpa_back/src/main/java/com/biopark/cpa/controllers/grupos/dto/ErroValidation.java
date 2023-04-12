package com.biopark.cpa.controllers.grupos.dto;

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
