package br.com.fortes.cigs.api.server;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint REST para gerenciamento de servidores da infraestrutura CIGS.
 */
@RestController
@RequestMapping("/api/v1/servers")
public class ServidorController {

    private final ServidorService service;

    public ServidorController(ServidorService service) {
        this.service = service;
    }

    /**
     * GET /api/v1/servers
     * Retorna a lista completa de servidores cadastrados.
     */
    @GetMapping                                              // ← FIX 1: anotação adicionada
    public ResponseEntity<List<ServidorDTO>> listarTodos() { // ← FIX 2: generics
        List<ServidorDTO> servidores = service.listarTodos();
        return ResponseEntity.ok(servidores);
    }

    /**
     * GET /api/v1/servers/{ip}
     * Busca um servidor específico pelo endereço IP.
     */
    @GetMapping("/{ip}")
    public ResponseEntity<ServidorDTO> buscarPorIp(@PathVariable String ip) {
        return service.buscarPorIp(ip)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}