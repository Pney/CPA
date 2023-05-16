package com.biopark.cpa.repository.pessoas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biopark.cpa.entities.pessoas.Funcionario;

import jakarta.transaction.Transactional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
    Optional<Funcionario> findByCracha(String cracha);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO funcionario (cracha, area, user_id)"
        +" VALUES (:#{#funcionario.cracha}, :#{#funcionario.area}, :#{#funcionario.user.id})"
        +" ON DUPLICATE KEY UPDATE cracha = VALUES(cracha), area = VALUES(area), user_id = VALUES(user_id)", nativeQuery = true)
    void upsert(@Param("funcionario") Funcionario funcionario);
}
