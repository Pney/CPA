package com.cpa.cpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpa.cpa.model.pessoas.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
