package com.biopark.cpa.entities.grupos;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_turma", nullable = false, unique = true)
    @NotBlank(message = "O campo codigo_turma não pode ser nulo")
    @CsvBindByName(column = "codigo_turma")
    private String codigoTurma;
}
