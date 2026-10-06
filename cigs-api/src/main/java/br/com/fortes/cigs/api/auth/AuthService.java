package br.com.fortes.cigs.api.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Autentica um usuário comparando a senha em texto puro com o hash BCrypt armazenado.
     * Se bem-sucedido, atualiza o campo lastLogin e persiste.
     *
     * @param username    nome de usuário
     * @param rawPassword senha em texto puro vinda do cliente
     * @return Optional contendo o Usuario autenticado, ou vazio se credenciais inválidas
     */
    @Transactional
    public Optional<Usuario> autenticar(String username, String rawPassword) {   // ← TIPADO
        Optional<Usuario> optUser = repository.findByUsername(username);          // ← TIPADO

        if (optUser.isPresent()) {
            Usuario user = optUser.get();
            if (passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
                user.setLastLogin(LocalDateTime.now());
                repository.save(user);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}