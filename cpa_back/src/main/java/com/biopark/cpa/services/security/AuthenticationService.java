package com.biopark.cpa.services.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biopark.cpa.entities.token.BlackListToken;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.entities.user.enums.Level;
import com.biopark.cpa.entities.user.enums.Role;
import com.biopark.cpa.repository.BlackListTokenRepository;
import com.biopark.cpa.repository.UserRepository;
import com.biopark.cpa.controllers.auth.requests.LoginRequest;
import com.biopark.cpa.controllers.auth.requests.RegisterRequest;
import com.biopark.cpa.controllers.auth.responses.AuthenticationResponse;

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
        var level = getLevelAccess(jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).level(level).build();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var level = getLevelAccess(jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).level(level).build();
    }

    public AuthenticationResponse authenticate(String token){
        return AuthenticationResponse.builder().token(token).level(getLevelAccess(token)).build();
    }

    private String getLevelAccess(String token){
        return jwtService.extractUserLevel(token);
    }

    public Boolean logout(String token){
        var expirationTime = jwtService.extractExpiration(token);
        BlackListToken tokenObj = BlackListToken.builder().token(token).dateExpiration(expirationTime).build();
        tokenRepository.save(tokenObj);
        SecurityContextHolder.clearContext();
        return true;
    }
}
