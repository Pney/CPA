package com.cpa.cpa.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpa.cpa.security.controllers.requests.AuthenticationRequest;
import com.cpa.cpa.security.controllers.requests.RegisterRequest;
import com.cpa.cpa.security.controllers.responses.AuthenticationResponse;
import com.cpa.cpa.security.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthenticationService service;

    @PostMapping("/public/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/public/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestHeader("Authorization") String token){
        token = token.substring(7);
        return ResponseEntity.ok(service.logout(token));
    }
}
