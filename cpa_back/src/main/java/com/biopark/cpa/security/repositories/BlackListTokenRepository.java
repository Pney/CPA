package com.biopark.cpa.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biopark.cpa.security.entities.BlackListToken;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long>{
    Optional<BlackListToken> findByToken(String token);
}
