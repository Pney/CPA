package com.biopark.cpa.services;

import org.springframework.stereotype.Service;

import com.biopark.cpa.controllers.cadastros.request.CadastroCpa;
import com.biopark.cpa.controllers.cadastros.response.ResponseMensagem;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.entities.user.enums.Level;
import com.biopark.cpa.entities.user.enums.Role;
import com.biopark.cpa.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembrosCPAService {
    private final UserRepository userRepository;

    public ResponseMensagem cadastrarCPA(CadastroCpa usuarioCPA) {
        if (userRepository.findByEmail(usuarioCPA.getEmail()).isPresent()) {
            return ResponseMensagem.builder().status("ERROR").mensagem("E-mail já cadastrado.").build();
        }

        User user = User.builder()
                .name(usuarioCPA.getName())
                .email(usuarioCPA.getEmail())
                .password(usuarioCPA.getPassword())
                .telefone(usuarioCPA.getTelefone())
                .level(Level.CPA)
                .role(Role.EXTERNO)
                .build();
        userRepository.save(user);
        return ResponseMensagem.builder().status("SUCCESS").mensagem("Usuário cadastrado com sucesso.").build();
    }

}
