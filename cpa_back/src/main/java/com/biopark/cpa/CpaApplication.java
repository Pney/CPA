package com.biopark.cpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(CpaApplication.class, args);
	}

}
