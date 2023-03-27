package com.cpa.cpa.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpa.cpa.security.entities.BlackListToken;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long>{
    Optional<BlackListToken> findByToken(String token);
}
