package com.biopark.cpa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.cpa.controllers.grupos.dto.EditarDTO;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.services.grupos.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PutMapping
    public ResponseEntity<EditarDTO> editarUser(@RequestBody User user) {
        EditarDTO response = userService.editarUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
