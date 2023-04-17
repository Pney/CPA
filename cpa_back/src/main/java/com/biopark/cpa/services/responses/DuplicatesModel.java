package com.biopark.cpa.services.responses;

import java.util.List;

import com.biopark.cpa.controllers.grupos.dto.ErroValidation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DuplicatesModel<T>{
    private List<ErroValidation> errors;
    private List<ErroValidation> warnings;
    private List<T> objects;
}
