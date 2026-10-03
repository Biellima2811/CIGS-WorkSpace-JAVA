package br.com.fortes.cigs.api.server;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Endpoint REST para gerenciamento de servidores da infraestrutura CIGS.
 *
 * Todas as respostas são serializadas em JSON. Segue semântica HTTP:
 * GET retorna 200, POST retorna 201, DELETE retorna 204, e ausência de
 * recurso retorna 404.
 *
 * Rotas expostas:
 *   GET    /api/v1/servers         — lista todos
 *   GET    /api/v1/servers/{ip}    — busca por IP
 *   POST   /api/v1/servers         — cadastra novo
 *   PUT    /api/v1/servers/{id}    — atualiza por ID
 *   DELETE /api/v1/servers/{id}    — remove por ID
 *
 * @author Gabriel Levi
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/servers")
public class ServidorController {

    private final ServidorService service;

    public ServidorController(ServidorService service) {
        this.service = service;
    }

    /**
     * Lista todos os servidores cadastrados.
     *
     * @return HTTP 200 com a lista de ServidorDTO (vazia se não houver registros)
     */
    @GetMapping
    public ResponseEntity<List<ServidorDTO>> listarTodos() {
        List<ServidorDTO> servidores = service.listarTodos();
        return ResponseEntity.ok(servidores);
    }

    /**
     * Busca um servidor específico pelo endereço IP.
     *
     * @param ip endereço IP do servidor
     * @return HTTP 200 com o DTO, ou HTTP 404 se não encontrado
     */
    @GetMapping("/{ip}")
    public ResponseEntity<ServidorDTO> buscarPorIp(@PathVariable String ip) {
        return service.buscarPorIp(ip)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Cadastra um novo servidor.
     * O payload passa por validação automática via @Valid.
     *
     * @param form dados do servidor em JSON
     * @return HTTP 201 com o DTO persistido (incluindo id gerado)
     */
    @PostMapping
    public ResponseEntity<ServidorDTO> criar(@RequestBody @Valid ServidorFormDTO form) {
        ServidorDTO criado = service.salvar(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * Atualiza os dados de um servidor existente.
     *
     * @param id   identificador do servidor
     * @param form dados novos
     * @return HTTP 200 com o DTO atualizado, ou HTTP 404 se o ID não existir
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServidorDTO> atualizar(@PathVariable Long id,
                                                 @RequestBody @Valid ServidorFormDTO form) {
        return service.atualizar(id, form)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Remove um servidor pelo ID.
     *
     * @param id identificador do servidor
     * @return HTTP 204 se removido, ou HTTP 404 se o ID não existir
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}