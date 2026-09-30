package br.com.fortes.cigs.api.server;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Serviço responsável pelas regras de negócio da gestão de servidores no CIGS.
 */
@Service
public class ServidorService {
	private final ServidorRepository repository;
	
	public ServidorService(ServidorRepository repository) {
		this.repository = repository;
	}
	
	/**
     * Retorna todos os servidores cadastrados convertidos para DTO.
     */
	@Transactional(readOnly = true)
	public List<ServidorDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(ServidorDTO::fromEntity)
                .toList();
    }
	
	/**
     * Busca um servidor pelo endereço IP.
     */
	@Transactional(readOnly = true)
	public Optional<ServidorDTO> buscarPorIp(String ip) {
		return repository.findByIp(ip).map(ServidorDTO::fromEntity);
	}
}
