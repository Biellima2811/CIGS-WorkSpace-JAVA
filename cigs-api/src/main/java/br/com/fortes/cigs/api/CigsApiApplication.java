package br.com.fortes.cigs.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CigsApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(CigsApiApplication.class, args);
        System.out.println(">>> CIGS API INICIADA E CONECTADA AO POSTGRESQL! <<<");	
	}
}
