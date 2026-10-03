package br.com.fortes.cigs.api.server;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Camada de serviço responsável pelas regras de negócio da gestão de servidores.
 *
 * Faz a ponte entre o Controller (camada HTTP) e o Repository (camada de persistência),
 * aplicando conversões de DTO, controle transacional e validações de negócio.
 *
 * @author Gabriel Levi
 * @since 1.0
 */
@Service
public class ServidorService {

    private final ServidorRepository repository;

    public ServidorService(ServidorRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todos os servidores cadastrados, convertidos para DTO seguro.
     *
     * @return lista de ServidorDTO, vazia se não houver registros
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
     *
     * @param ip endereço IP a ser buscado
     * @return Optional contendo o DTO, ou vazio se não existir
     */
    @Transactional(readOnly = true)
    public Optional<ServidorDTO> buscarPorIp(String ip) {
        return repository.findByIp(ip).map(ServidorDTO::fromEntity);
    }

    /**
     * Persiste um novo servidor a partir do formulário de entrada.
     * O campo criadoEm é preenchido automaticamente com a data/hora atual.
     *
     * @param form dados do servidor a cadastrar
     * @return DTO do servidor persistido, com id e criadoEm preenchidos
     */
    @Transactional
    public ServidorDTO salvar(ServidorFormDTO form) {
        Servidor entity = form.toEntity();
        entity.setCriadoEm(LocalDateTime.now());
        Servidor salvo = repository.save(entity);
        return ServidorDTO.fromEntity(salvo);
    }

    /**
     * Atualiza os dados de um servidor existente.
     * A senha específica só é sobrescrita se um valor não-vazio for enviado,
     * permitindo editar os demais campos sem precisar reenviar a senha.
     *
     * @param id   identificador do servidor
     * @param form dados novos a aplicar
     * @return Optional com o DTO atualizado, ou vazio se o ID não existir
     */
    @Transactional
    public Optional<ServidorDTO> atualizar(Long id, ServidorFormDTO form) {
        return repository.findById(id).map(servidor -> {
            servidor.setIp(form.ip());
            servidor.setHostname(form.hostname());
            servidor.setIpPublico(form.ipPublico());
            servidor.setFuncao(form.funcao());
            servidor.setCliente(form.cliente());
            servidor.setUsuarioEspecifico(form.usuarioEspecifico());

            if (form.senhaEspecifica() != null && !form.senhaEspecifica().isBlank()) {
                servidor.setSenhaEspecifica(form.senhaEspecifica());
            }

            return ServidorDTO.fromEntity(repository.save(servidor));
        });
    }

    /**
     * Remove um servidor pelo seu identificador.
     *
     * @param id identificador do servidor
     * @return true se o registro existia e foi removido, false caso contrário
     */
    @Transactional
    public boolean deletarPorId(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}