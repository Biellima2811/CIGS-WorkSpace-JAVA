package br.com.fortes.cigs.agent.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cigs")
public class AgentController {
	private static final String VERSAO_AGENTE = "v4.0 (Java Edition)";
	
	/**
     * Equivalente à rota @app.route('/cigs/status') do Python.
     * Retorna os dados vitais do servidor onde o Agente está instalado.
     */
	@GetMapping("/status")
	public ResponseEntity<Map<String, Object>> status(
			@RequestParam(defaultValue = "AC") String sistema,
			@RequestParam(defaultValue = "0") String full){
		boolean isFull = "1".equals(full);
		
		int qtdClientesAtivos = 0;
		String referenciaCliente = "N/A";
		double diskFree = 0.0;
		double ramUsage = 0.0;
	}
	
}
