package com.biopark.cpa.services.grupos;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biopark.cpa.controllers.grupos.dto.EditarDTO;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User buscarPorEmail(String email) {
        var optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }

    public User buscarPorId(Long id) {
        var optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }

    public EditarDTO editarUser(User UserRequest) {
        try {
            User user = buscarPorId(UserRequest.getId());
            user.setName(UserRequest.getName());
            user.setTelefone(UserRequest.getTelefone());
            user.setEmail(UserRequest.getEmail());
            user.setPassword(passwordEncoder.encode(UserRequest.getPassword()));
            userRepository.save(user);
            return EditarDTO.builder().status(HttpStatus.OK)
                    .mensagem("Usuario" + UserRequest.getId() + " editado com sucesso").build();
        } catch (Exception e) {
            return EditarDTO.builder().status(HttpStatus.NOT_FOUND).mensagem(e.getMessage()).build();
        }
    }
}
