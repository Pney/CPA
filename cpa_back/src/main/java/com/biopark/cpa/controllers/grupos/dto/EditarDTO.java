package com.biopark.cpa.controllers.grupos.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EditarDTO {
    private HttpStatus status;
    private String mensagem;
}
