package com.biopark.cpa.repository.pessoas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biopark.cpa.entities.pessoas.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
    Optional<Professor> findByCracha(String cracha);
}
