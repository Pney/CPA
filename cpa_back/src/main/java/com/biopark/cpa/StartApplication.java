package com.biopark.cpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.entities.user.enums.Level;
import com.biopark.cpa.entities.user.enums.Role;
import com.biopark.cpa.repository.pessoas.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StartApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User userAdmin = User.builder()
                .cpf("113.015.639-78")
                .email("admin@gmail.com")
                .name("admin")
                .level(Level.CPA)
                .telefone("44998139378")
                .role(Role.EXTERNO)
                .password(passwordEncoder.encode("12345678"))
                .build();

        userRepository.save(userAdmin);

        User user = User.builder()
                .cpf("114.035.789-78")
                .email("user@gmail.com")
                .name("user")
                .level(Level.USER)
                .telefone("546546546466")
                .role(Role.EXTERNO)
                .password(passwordEncoder.encode("12345678"))
                .build();

        userRepository.save(user);
    }
}
