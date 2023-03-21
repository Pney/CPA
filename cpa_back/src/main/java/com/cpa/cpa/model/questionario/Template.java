package com.cpa.cpa.model.questionario;

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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTemplate;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne()
    @JoinColumn(name = "eixo_id", referencedColumnName = "idEixo")
    Eixo eixo;

    @ManyToMany(mappedBy="templates")
    private List<Questao> questoes = new ArrayList<>();

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<Questionario> questionarios = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;
}
