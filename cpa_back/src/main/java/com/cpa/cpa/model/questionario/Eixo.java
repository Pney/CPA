package com.cpa.cpa.model.questionario;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Eixo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEixo;

    @Column(nullable = false)
    private String eixo;

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "eixo", cascade = CascadeType.ALL)
    private List<Questao> questoes = new ArrayList<>();

    @OneToMany(mappedBy = "eixo", cascade = CascadeType.ALL)
    private List<Template> templates = new ArrayList<>();
}
