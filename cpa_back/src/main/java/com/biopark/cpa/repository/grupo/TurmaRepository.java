package com.biopark.cpa.repository.grupo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biopark.cpa.entities.grupos.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByCodigoTurma(String codigoTurma);
}