package com.biopark.cpa.services.pessoas;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.dto.GenericDTO;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.entities.user.enums.Level;
import com.biopark.cpa.entities.user.enums.Role;
import com.biopark.cpa.form.pessoas.CadastroCPA;
import com.biopark.cpa.repository.pessoas.UserRepository;
import com.biopark.cpa.services.security.GeneratePassword;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembrosCPAService {
    private final UserRepository userRepository;
    private final Validator validator;
    private final GeneratePassword generatePassword;

    public GenericDTO cadastrarCPA(CadastroCPA usuarioCPA) {
        Set<ConstraintViolation<CadastroCPA>> violacoes = validator.validate(usuarioCPA);

        if (!violacoes.isEmpty()) {
            String mensagem = "";
            for (ConstraintViolation<CadastroCPA> violacao : violacoes) {
                mensagem += violacao.getMessage()+"; ";
            }
            return GenericDTO.builder().status(HttpStatus.BAD_REQUEST).mensagem(mensagem).build();
        }

        if ((userRepository.findByEmail(usuarioCPA.getEmail()).isPresent())|(userRepository.findByCpf(usuarioCPA.getCpf()).isPresent())) {
            return GenericDTO.builder().status(HttpStatus.CONFLICT).mensagem("Usuario já cadastrado").build();
        }

        User user = User.builder()
                .name(usuarioCPA.getName())
                .cpf(usuarioCPA.getCpf())
                .email(usuarioCPA.getEmail())
                .password(generatePassword.getPwd())
                .telefone(usuarioCPA.getTelefone())
                .level(Level.CPA)
                .role(Role.EXTERNO)
                .build();

        userRepository.save(user);
        return GenericDTO.builder().status(HttpStatus.OK).mensagem("Usuário cadastrado com sucesso.").build();
    }

}
