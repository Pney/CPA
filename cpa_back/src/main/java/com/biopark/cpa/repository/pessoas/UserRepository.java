package com.biopark.cpa.repository.pessoas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biopark.cpa.entities.user.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user (cpf, name, telefone, email, password, role, level)"
        +" VALUES (:#{#user.cpf}, :#{#user.name}, :#{#user.telefone}, :#{#user.email}, :#{#user.password},"
        +" :#{#user.role}, :#{#user.level})"
        +" ON DUPLICATE KEY UPDATE cpf = VALUES(cpf), name = VALUES(name), telefone = VALUES(telefone), email = VALUES(email),"
        +" password = VALUES(password), role = VALUES(role), level = VALUES(level)", nativeQuery = true)
    void upsert(@Param("user") User user);
}
