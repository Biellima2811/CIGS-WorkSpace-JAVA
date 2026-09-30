package br.com.fortes.cigs.api.server;

import java.time.LocalDateTime;

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
     * Converte uma entidade JPA Servidor para ServidorDTO.
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
