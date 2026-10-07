package br.com.fortes.cigs.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgentApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgentApplication.class, args);
		System.out.println(">>> CIGS AGENTE JAVA INICIANDO NA PORTA 5580 <<<");
	}
}
