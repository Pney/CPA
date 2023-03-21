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
import jakarta.persistence.OneToMany;

@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisciplina;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL)
    private List<ProfessorTurmaDisciplina> professoresTurmasDisciplinas = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;    
}
