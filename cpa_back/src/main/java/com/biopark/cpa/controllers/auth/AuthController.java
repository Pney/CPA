package com.biopark.cpa.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.cpa.dto.auth.AuthenticationResponse;
import com.biopark.cpa.form.auth.LoginRequest;
import com.biopark.cpa.services.security.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthenticationService service;

    @PostMapping("/public/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        AuthenticationResponse authenticationResponse = service.authenticate(request);
        return ResponseEntity.status(authenticationResponse.getStatus()).body(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateToken(@RequestHeader("Authorization") String token){
        token = token.substring(7);
        return ResponseEntity.ok(service.authenticate(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestHeader("Authorization") String token) {
        token = token.substring(7);
        return ResponseEntity.ok(service.logout(token));
    }
}
