package com.biopark.cpa.repository.pessoas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biopark.cpa.entities.pessoas.Professor;

import jakarta.transaction.Transactional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
    Optional<Professor> findByCracha(String cracha);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO professor (cracha, is_coordenador, user_id)"+
        " VALUES (:#{#professor.cracha}, :#{#professor.is_coordenador}, :#{#professor.user.id})"
        +" ON DUPLICATE KEY UPDATE", nativeQuery = true)
    void upsert(@Param("professor") Professor professor);
}
