package com.biopark.cpa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.cpa.controllers.grupos.dto.GenericDTO;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.services.grupos.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

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
