package com.biopark.cpa.repository.grupo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biopark.cpa.entities.grupos.Instituicao;

import jakarta.transaction.Transactional;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    Optional<Instituicao> findByCodigoInstituicao(String codigoInstituicao);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Instituicao (nome_instituicao, email, cnpj, codigo_instituicao) VALUES (:#{#instituicoes.{nomeInstituicao}}, :#{#instituicoes.{email}}, :#{#instituicoes.{cnpj}}, :#{#instituicoes.{codigoInstituicao}}) ON DUPLICATE KEY UPDATE nome_instituicao = VALUES(nome_instituicao), email = VALUES(email), cnpj = VALUES(cnpj)", nativeQuery = true)
    void upsert(@Param("instituicoes") List<Instituicao> instituicoes);
}
