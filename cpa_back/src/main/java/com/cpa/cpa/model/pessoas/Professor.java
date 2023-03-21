package com.cpa.cpa.model.pessoas;

import java.time.LocalDateTime;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfessor;

    @Column(nullable = false, unique = true)
    private String cracha;

    @Column(nullable = false)
    private boolean isCoordenador;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<ProfessorTurmaDisciplina> professoresTurmasDisciplinas = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "idUsuario")
    private User user;
}
