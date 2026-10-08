package br.com.fortes.cigs.agent.controller;

import br.com.fortes.cigs.agent.service.AgentMetricsService;
import java.util.HashMap;
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
    private final AgentMetricsService metricsService;

    // Injeção do serviço via construtor
    public AgentController(AgentMetricsService metricsService) {
        this.metricsService = metricsService;
    }
    
    /**
     * Equivalente à rota @app.route('/cigs/status') do Python. Retorna os dados
     * vitais do servidor onde o Agente está instalado.
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status(
            @RequestParam(defaultValue = "AC") String sistema,
            @RequestParam(defaultValue = "0") String full) {
        boolean isFull = "1".equals(full);

        int qtdClientesAtivos = metricsService.contarClientesAtivos();
        String referenciaCliente = "N/A";
        double diskFree = 0.0;
        double ramUsage = 0.0;

        if (isFull) {
            // Valores simulados de hardware por enquanto
            diskFree = Math.round(metricsService.obterEspacoLivreDiscoC() * 100.0) / 100.0;
            ramUsage = Math.round(metricsService.obterUsoMemoriaRam() * 100.0) / 100.0;
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ONLINE");
        response.put("version", VERSAO_AGENTE);
        response.put("hash", "java_hash_placeholder");
        response.put("clientes", qtdClientesAtivos);
        response.put("ref", referenciaCliente);
        response.put("sistema_lido", sistema);
        response.put("disk", diskFree);
        response.put("ram", ramUsage);
        return ResponseEntity.ok(response);
    }

}
