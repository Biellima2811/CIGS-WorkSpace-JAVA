package br.com.fortes.cigs.api.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo username único.
     *
     * @param username nome de usuário
     * @return Optional contendo o Usuario se encontrado
     */
    Optional<Usuario> findByUsername(String username);
}