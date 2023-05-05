package com.biopark.cpa.form.cadastroCsv;

import com.biopark.cpa.entities.user.enums.Level;
import com.biopark.cpa.entities.user.enums.Role;
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
public class ProfessorModel {
    @CsvBindByName(column = "nome")
    @NotBlank(message = "O campo nome é obrigatório")
    private String name;

    @CsvBindByName(column = "telefone")
    @NotBlank(message = "O campo telefone é obrigatório")
    @Pattern(regexp = "[^Aa-zZ]", message = "Telefone em formato inválido")
    private String telefone;

    @CsvBindByName(column = "email")
    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "Email com formato inválido")
    private String email;
    
    @CsvBindByName(column = "cracha")
    @NotBlank(message = "O campo crachá é obrigatório")
    private String cracha;
    
    private final Role role = Role.PROFESSOR;
    private final Level level = Level.USER;
    private final boolean isCoordenador = false;
}
