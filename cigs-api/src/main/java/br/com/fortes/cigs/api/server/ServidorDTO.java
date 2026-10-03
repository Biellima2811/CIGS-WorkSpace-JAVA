package br.com.fortes.cigs.api.server;

import java.time.LocalDateTime;

/**
 * DTO de resposta que representa um servidor no formato seguro para tráfego HTTP.
 * A senha específica é omitida intencionalmente para evitar vazamento de credenciais.
 *
 * @param id                identificador único no banco (gerado pelo PostgreSQL)
 * @param ip                endereço IP local/VPN (identificador de negócio)
 * @param hostname          nome da máquina na rede
 * @param ipPublico         endereço IP público (WAN)
 * @param funcao            papel do servidor (App, Banco, Web...)
 * @param cliente           nome do cliente ou projeto associado
 * @param usuarioEspecifico usuário de rede customizado (pode ser null)
 * @param criadoEm          data e hora de criação do registro
 *
 * @author Gabriel Levi
 * @since 1.0
 */
public record ServidorDTO(
        Long id,
        String ip,
        String hostname,
        String ipPublico,
        String funcao,
        String cliente,
        String usuarioEspecifico,
        LocalDateTime criadoEm
) {

    /**
     * Converte uma entidade JPA Servidor em DTO de resposta.
     *
     * @param entity entidade carregada do banco
     * @return novo ServidorDTO populado com os campos seguros
     */
    public static ServidorDTO fromEntity(Servidor entity) {
        return new ServidorDTO(
                entity.getId(),
                entity.getIp(),
                entity.getHostname(),
                entity.getIpPublico(),
                entity.getFuncao(),
                entity.getCliente(),
                entity.getUsuarioEspecifico(),
                entity.getCriadoEm()
        );
    }
}