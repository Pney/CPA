package com.biopark.cpa.form.cadastroCsv;

import com.opencsv.bean.CsvBindByName;

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
public class ProfessorModel {
    @CsvBindByName(column = "nome")
    @NotBlank(message = "O campo nome é obrigatório")
    private String name;

    @CsvBindByName(column = "telefone")
    @NotBlank(message = "O campo telefone é obrigatório")
    @Pattern(regexp = "^[0-9]+$", message = "Telefone em formato inválido")
    private String telefone;

    @CsvBindByName(column = "e-mail")
    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "Email com formato inválido")
    private String email;
    
    @CsvBindByName(column = "cracha")
    @NotBlank(message = "O campo crachá é obrigatório")
    private String cracha;

    @CsvBindByName(column = "cpf")
    @NotBlank(message = "O campo cpf é obrigatório")
    @Pattern(regexp = "(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})", message = "cpf em formato inválido")
    private String cpf;
}
