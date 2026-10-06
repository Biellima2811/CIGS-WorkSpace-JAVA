package br.com.fortes.cigs.api.auth;

import org.springframework.http.HttpStatus;        // ← ADICIONE
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request) {
        return authService.autenticar(request.username(), request.password())
                .map(u -> ResponseEntity.ok("Autenticação bem-sucedida! Patente: " + u.getRole()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Acesso Negado: Usuário ou senha incorretos."));
    }
}