package com.example.vendas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
public class MinhaConfiguration {
	@Bean
	public CommandLineRunner executar() {
		return args -> {
			System.out.println("Ambiente de Desenvolvimento"); 
		};
	}  
}
