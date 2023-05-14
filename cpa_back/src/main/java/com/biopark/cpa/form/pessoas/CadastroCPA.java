package com.biopark.cpa.form.pessoas;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CadastroCPA {
   @NotBlank(message = "cpf é um campo obrigatório")
   @Pattern(regexp = "(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})", message = "cpf em formato inválido")
   private String cpf;

   @NotBlank(message = "nome é um campo obrigatório")
   private String name;
   
   @NotBlank(message = "telefone é um campo obrigatório")
   private String telefone;

   @NotBlank(message = "email é um campo obrigatório")
   @Email(message = "formato de email inválido")
   private String email;
}
