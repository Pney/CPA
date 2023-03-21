package com.cpa.cpa.model.grupos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.cpa.cpa.model.pessoas.Aluno;
import com.cpa.cpa.model.pessoas.Professor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class ProfessorTurmaDisciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfessorCursoDisciplina;
    
    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "idProfessor")
    private Professor professor;
    
    @ManyToOne
    @JoinColumn(name = "turma_id", referencedColumnName = "idTurma")
    private Turma turma;
    
    @ManyToOne
    @JoinColumn(name = "disciplina_id", referencedColumnName = "idDisciplina")
    private Disciplina disciplina;

    @ManyToMany(mappedBy="professoresTurmasDisciplinas")
    private List<Aluno> alunos = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;
    
    @LastModifiedDate
    private LocalDateTime updated_at;
}
