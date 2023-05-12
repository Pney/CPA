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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_curso", nullable = false, unique = true)
    @NotBlank(message = "O campo nome não deve ser nulo")
    @CsvBindByName(column = "nome")
    @ColumnTransformer(write = "LOWER(?)")
    private String nomeCurso;

    @Column(name = "cod_curso", nullable = false, unique = true)
    @NotBlank(message = "O campo cod curso não deve ser nulo")
    @CsvBindByName(column = "codigo curso")
    @ColumnTransformer(write = "LOWER(?)")
    private String codCurso;
    
    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @OneToMany(mappedBy = "curso")
    private List<Turma> turmas;

    @Transient
    @NotBlank(message = "O campo cod insituicao não deve ser nulo")
    @CsvBindByName(column = "codigo instituicao")
    private String codInstituicao;
}
