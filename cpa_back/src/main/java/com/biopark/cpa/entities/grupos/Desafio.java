package com.biopark.cpa.entities.grupos;

import java.util.List;

import org.hibernate.annotations.ColumnTransformer;

import com.opencsv.bean.CsvBindByName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "desafio")
public class Desafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_desafio", nullable = false, unique = true)
    @NotBlank(message = "O campo nome_desafio não pode ser nulo")
    @CsvBindByName(column = "nome_desafio")
    @ColumnTransformer(write = "LOWER(?)")
    private String nomeDesafio;

    //@Column(name="Desativados")
    //private Boolean Desativados;
    @ManyToMany
    @JoinTable(name = "desafio_turma",
               joinColumns = @JoinColumn(name = "desafio_id"),
               inverseJoinColumns = @JoinColumn(name = "turma_id"))
    private List<Turma> turmas;
}
