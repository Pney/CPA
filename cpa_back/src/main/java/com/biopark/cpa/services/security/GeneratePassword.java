package com.biopark.cpa.services.security;

import java.security.SecureRandom;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class GeneratePassword {
    private final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*()_+-=[]|,./?><";
    private final int TAMANHO = 8;

    public String getPwd(){
        SecureRandom random = new SecureRandom();
        StringBuilder pwd = new StringBuilder(TAMANHO);

        for (int i = 0; i < TAMANHO; i++) {
            int indice = random.nextInt(CARACTERES.length());
            char caractere = CARACTERES.charAt(indice);
            pwd.append(caractere); 
        }

        return pwd.toString();
    }

}
