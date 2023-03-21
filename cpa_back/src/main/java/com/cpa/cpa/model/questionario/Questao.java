package com.cpa.cpa.model.questionario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
@Entity
public class Questao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuestao;

    @Column(columnDefinition = "json")
    private String opcoes;

    @Column
    private TipoQuestao tipoQuestao;
   
    @ManyToOne()
    @JoinColumn(name = "eixo_id", referencedColumnName = "idEixo")
    Eixo eixo;

    @ManyToMany()
    @JoinTable(name = "questao_template", joinColumns = @JoinColumn(name="questao_id"), inverseJoinColumns = @JoinColumn(name="template_id"))
    List<Template> templates = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;
    
    @LastModifiedDate
    private LocalDateTime updated_at; 
}
