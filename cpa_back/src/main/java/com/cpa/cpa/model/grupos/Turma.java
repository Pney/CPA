package com.cpa.cpa.model.grupos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurma;

    @ManyToOne
    @JoinColumn(name = "curso_id", referencedColumnName = "idCurso")
    private Curso curso;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<ProfessorTurmaDisciplina> professoresTurmasDisciplinas = new ArrayList<>();

    @Column(nullable = false)
    private String codTurma;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;    
}
