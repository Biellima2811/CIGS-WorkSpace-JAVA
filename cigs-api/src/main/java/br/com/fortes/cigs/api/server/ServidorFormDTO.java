package br.com.fortes.cigs.api.server;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada usado nos endpoints de criação (POST) e atualização (PUT).
 * Não expõe campos gerenciados pelo banco (id, criadoEm), evitando que o cliente
 * tente manipulá-los.
 *
 * A validação é aplicada automaticamente pelo Spring quando o Controller anota
 * o parâmetro com @Valid. Em caso de falha, retorna HTTP 400 com o detalhamento.
 *
 * @param ip                endereço IP do servidor (obrigatório)
 * @param hostname          nome da máquina na rede
 * @param ipPublico         endereço IP público
 * @param funcao            papel do servidor
 * @param cliente           nome do cliente/projeto
 * @param usuarioEspecifico usuário de rede customizado (opcional)
 * @param senhaEspecifica   senha de rede/RDP (opcional, campo sensível)
 *
 * @author Gabriel Levi
 * @since 1.0
 */
public record ServidorFormDTO(

        @NotBlank(message = "O IP é obrigatório")
        String ip,

        String hostname,
        String ipPublico,
        String funcao,
        String cliente,
        String usuarioEspecifico,
        String senhaEspecifica

) {

    /**
     * Converte este formulário em uma nova entidade JPA Servidor.
     * Não define id nem criadoEm — ambos são gerenciados pelo banco e pela camada de serviço.
     *
     * @return nova instância de Servidor pronta para persistência
     */
    public Servidor toEntity() {
        Servidor s = new Servidor();
        s.setIp(this.ip);
        s.setHostname(this.hostname);
        s.setIpPublico(this.ipPublico);
        s.setFuncao(this.funcao);
        s.setCliente(this.cliente);
        s.setUsuarioEspecifico(this.usuarioEspecifico);
        s.setSenhaEspecifica(this.senhaEspecifica);
        return s;
    }
}