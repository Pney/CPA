package com.biopark.cpa.dto.cadastroCsv;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationModel<T>{
    private List<ErroValidation> errors;
    private List<ErroValidation> warnings;
    private List<T> objects;
}
