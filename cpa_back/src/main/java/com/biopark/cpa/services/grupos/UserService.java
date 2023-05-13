package com.biopark.cpa.services.grupos;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biopark.cpa.dto.GenericDTO;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.repository.pessoas.UserRepository;

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

    public List<User> buscarTodos() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("Não há usuários cadastrados!");
        }
        return users;
    }
    

    public GenericDTO editarUser(User UserRequest) {
        try {
            User user = buscarPorId(UserRequest.getId());
            user.setName(UserRequest.getName());
            user.setTelefone(UserRequest.getTelefone());
            user.setEmail(UserRequest.getEmail());
            user.setPassword(passwordEncoder.encode(UserRequest.getPassword()));
            userRepository.save(user);
            return GenericDTO.builder().status(HttpStatus.OK)
                    .mensagem("Usuario" + UserRequest.getId() + " editado com sucesso").build();
        } catch (Exception e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem(e.getMessage()).build();
        }
    }

    public GenericDTO excluirUser(Long id) {
        try {
            var userDB = userRepository.findById(id);
            if (!userDB.isPresent()) {
                return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem("usuário não encontrado").build();
            }
            User user = userDB.get();
            userRepository.delete(user);
            return GenericDTO.builder().status(HttpStatus.OK)
                    .mensagem("Usuário " + user.getName() + " excluído com sucesso")
                    .build();
        } catch (EmptyResultDataAccessException e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND)
                    .mensagem("Usuário " + id + " não encontrado")
                    .build();
        }
    }

}
