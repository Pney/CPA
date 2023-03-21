package com.cpa.cpa.model.pessoas;

import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.cpa.cpa.model.grupos.ProfessorTurmaDisciplina;

// import com.cpa.cpa.model.grupos.ProfessorTurmaDisciplina;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAluno;

    @Column(nullable = false, unique = true)
    private String ra;

    @Column(nullable = false, unique = true)
    private String cpf;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @ManyToMany()
    @JoinTable(name = "aluno_discplina", joinColumns = @JoinColumn(name="aluno_id"), inverseJoinColumns = @JoinColumn(name="disciplina_professor_turma_id"))
    private List<ProfessorTurmaDisciplina> professoresTurmasDisciplinas = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "idUsuario")
    private User user;    
}
