package com.cpa.cpa.model.questionario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.cpa.cpa.model.grupos.Curso;
import com.cpa.cpa.model.grupos.Instituicao;
import com.cpa.cpa.model.grupos.ProfessorTurmaDisciplina;
import com.cpa.cpa.model.pessoas.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
@Entity
public class Questionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idQuestionario;
    
    @Column(nullable = false)
    boolean isClosed;

    @Column(nullable = false)
    String nomeQuestionario;
    
    @ManyToOne()
    @JoinColumn(name = "templete_id", referencedColumnName = "idTemplate")
    Template template;
    
    @ManyToMany(mappedBy="questionarios")
    List<User> users = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "instituicao_id", referencedColumnName = "idInstituicao")
    Instituicao instituicao;

    @ManyToOne
    @JoinColumn(name = "curso_id", referencedColumnName = "idCurso")
    Curso curso;
    
    @ManyToOne
    @JoinColumn(name = "professor_curso_disciplina_id", referencedColumnName = "idProfessorCursoDisciplina")
    ProfessorTurmaDisciplina professorTurmaDisciplina;
    
    @CreatedDate
    @Column(nullable = false)
    LocalDateTime dataInicio;
    
    @LastModifiedDate
    LocalDateTime dataFim;
}
