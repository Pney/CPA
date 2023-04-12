package com.biopark.cpa.repository.grupo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biopark.cpa.entities.grupos.Desafio;

@Repository
public interface DesafioRepository extends JpaRepository<Desafio, Long>{
    Optional<Desafio> findByNomeDesafio(String nomeDesafio);
}
