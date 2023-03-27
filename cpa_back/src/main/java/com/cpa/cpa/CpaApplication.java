package com.cpa.cpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cpa.cpa.security.controllers.requests.RegisterRequest;
import com.cpa.cpa.security.services.AuthenticationService;


@SpringBootApplication
public class CpaApplication {

	private static RegisterRequest request;
	private static AuthenticationService service;
	public static void main(String[] args) {
		SpringApplication.run(CpaApplication.class, args);
		request.setEmail("teste@gmail.com");
		request.setPassword("teste123456");
		service.register(request);
	}

}
