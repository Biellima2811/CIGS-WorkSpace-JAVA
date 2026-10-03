package br.com.fortes.cigs.api.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório JPA responsável pela persistência de Servidor.
 *
 * Herda de JpaRepository todas as operações CRUD básicas (save, findById, findAll,
 * deleteById, existsById, count). Métodos adicionais são gerados automaticamente
 * pelo Spring Data a partir da convenção de nomes.
 *
 * @author Gabriel Levi
 * @since 1.0
 */
@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    /**
     * Busca um servidor pelo endereço IP, que é o identificador de negócio único.
     *
     * @param ip endereço IP do servidor
     * @return Optional contendo o Servidor se encontrado, ou vazio caso contrário
     */
    Optional<Servidor> findByIp(String ip);
}