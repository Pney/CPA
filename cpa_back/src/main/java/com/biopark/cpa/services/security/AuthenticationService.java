package com.biopark.cpa.services.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.biopark.cpa.entities.token.BlackListToken;
import com.biopark.cpa.form.auth.LoginRequest;
import com.biopark.cpa.repository.auth.BlackListTokenRepository;
import com.biopark.cpa.repository.pessoas.UserRepository;
import com.biopark.cpa.dto.auth.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BlackListTokenRepository tokenRepository;

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        var user = repository.findByEmail(request.getEmail());

        if (!user.isPresent()) {
            return AuthenticationResponse.builder().token(null).status(HttpStatus.FORBIDDEN).level(null).build();
        }

        var jwtToken = jwtService.generateToken(user.get());
        var level = getLevelAccess(jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).status(HttpStatus.OK).level(level).build();
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
