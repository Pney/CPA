package com.biopark.cpa.entities.grupos;

import java.util.List;

import org.hibernate.annotations.ColumnTransformer;

import com.opencsv.bean.CsvBindByName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "instituicao")
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nome_instituicao")
    @NotBlank(message = "O campo nome da instituição não pode ser nulo")
    @CsvBindByName(column = "nome instituicao")
    @ColumnTransformer(write = "LOWER(?)")
    private String nomeInstituicao;

    @Column(nullable = false)
    @NotBlank(message = "O campo de email não pode ser nulo")
    @Email(message = "formato de email invalido")
    @CsvBindByName(column = "e-mail")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "O campo de CNPJ não pode ser nulo")
    @Pattern(regexp = "(\\d{2}\\.[0-9]{3}\\.\\d{3}\\/\\d{4}-\\d{2})", message = "O valor informado não esta no modelo de cnpj")
    @CsvBindByName(column = "cnpj")
    private String cnpj;

    @Column(nullable = false, unique = true, name = "codigo_instituicao")
    @NotBlank(message = "O campo de código da instituição não pode ser nulo")
    @CsvBindByName(column = "codigo instituicao")
    @ColumnTransformer(write = "LOWER(?)")
    private String codigoInstituicao;

    @OneToMany(mappedBy = "instituicao")
    private List<Curso> cursos;

}
