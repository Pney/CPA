package com.cpa.cpa.security.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cpa.cpa.pessoas.repositories.UserRepository;
import com.cpa.cpa.pessoas.user.entities.User;
import com.cpa.cpa.pessoas.user.entities.enums.Level;
import com.cpa.cpa.pessoas.user.entities.enums.Role;
import com.cpa.cpa.security.controllers.requests.AuthenticationRequest;
import com.cpa.cpa.security.controllers.requests.RegisterRequest;
import com.cpa.cpa.security.controllers.responses.AuthenticationResponse;
import com.cpa.cpa.security.entities.BlackListToken;
import com.cpa.cpa.security.repositories.BlackListTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BlackListTokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ALUNO)
                .level(Level.CPA)
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public Boolean logout(String token){
        BlackListToken tokenObj = BlackListToken.builder().token(token).build();
        tokenRepository.save(tokenObj);
        return true;
    }
}
