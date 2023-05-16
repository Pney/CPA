package com.biopark.cpa.form.cadastroCsv;

import com.opencsv.bean.CsvBindByName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FuncionarioModel {
    @CsvBindByName(column = "cpf")
    @NotBlank(message = "cpf não deve ser nulo")
    @Pattern(regexp = "(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})", message = "cpf em formato inválido")
    private String cpf;

    @CsvBindByName(column = "nome")
    @NotBlank(message = "nome não deve ser nulo")
    private String name;

    @CsvBindByName(column = "telefone")
    @NotBlank(message = "telefone não deve ser nulo")
    private String telefone;

    @CsvBindByName(column = "e-mail")
    @NotBlank(message = "email não deve ser nulo")
    @Email(message = "formato de email inválido")
    private String email;

    @CsvBindByName(column = "cracha")
    @NotBlank(message = "cracha não deve ser nulo")
    private String cracha;

    @CsvBindByName(column = "area")
    @NotBlank(message = "area não deve ser nula")
    private String area;
}
