package com.biopark.cpa.repository.grupo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biopark.cpa.entities.grupos.Turma;

import jakarta.transaction.Transactional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByCodTurma(String codTurma);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Turma (cod_turma, nome_turma, semestre) VALUES (:#{#turma.codTurma}, :#{#turma.nomeTurma}, :#{#turma.semestre}) ON DUPLICATE KEY UPDATE nome_turma = VALUES(nome_turma), semestre = VALUES(semestre)", nativeQuery = true)
    void upsert(@Param("turma") Turma turma);
}