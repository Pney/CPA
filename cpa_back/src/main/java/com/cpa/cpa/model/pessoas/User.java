package com.cpa.cpa.model.pessoas;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isCPA;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private UserRole userRole;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;    
}
