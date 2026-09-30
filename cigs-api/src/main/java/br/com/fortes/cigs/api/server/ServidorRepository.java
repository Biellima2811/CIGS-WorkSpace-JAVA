package br.com.fortes.cigs.api.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    /**
     * Busca um servidor pelo IP (identificador de negócio único).
     *
     * @param ip endereço IP do servidor
     * @return Optional contendo o servidor se encontrado
     */
    Optional<Servidor> findByIp(String ip);
}