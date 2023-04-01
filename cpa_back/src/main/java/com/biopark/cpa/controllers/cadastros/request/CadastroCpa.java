package com.biopark.cpa.controllers.cadastros.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CadastroCpa {
   private String name;
   private String telefone;
   private String email;
   private String password;
}
