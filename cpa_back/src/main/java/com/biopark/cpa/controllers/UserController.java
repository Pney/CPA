package com.biopark.cpa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.cpa.dto.GenericDTO;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.repository.pessoas.UserRepository;
import com.biopark.cpa.services.grupos.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
  
    @GetMapping
    public ResponseEntity<Optional<User>> buscarPorId (@RequestParam(name = "id") Long id) {
            var user = userRepository.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
            }
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    
    @PutMapping
    public ResponseEntity<GenericDTO> editarUser(@RequestBody User user) {
        GenericDTO response = userService.editarUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<GenericDTO> excluirUser(@PathVariable Long id) {
        GenericDTO response = userService.excluirUser(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
