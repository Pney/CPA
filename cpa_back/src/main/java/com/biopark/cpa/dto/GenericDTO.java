package com.biopark.cpa.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenericDTO {
    private HttpStatus status;
    private String mensagem;
}
