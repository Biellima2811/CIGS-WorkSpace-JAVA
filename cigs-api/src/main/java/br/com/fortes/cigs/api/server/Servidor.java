package br.com.fortes.cigs.api.server;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "servidores")
public class Servidor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String ip;
	
	private String hostname;
	
	@Column(name = "ip_publico")
	private String ipPublico;
	
	private String funcao;
	private String cliente;
	
	@Column(name = "usuario_especifico")
	private String usuarioEspecifico;
	
	@Column(name = "senha_especifica")
	private String senhaEspecifica;
	
	@Column(name = "criado_em")
	private LocalDateTime criadoEm;

	public Servidor() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIpPublico() {
		return ipPublico;
	}

	public void setIpPublico(String ipPublico) {
		this.ipPublico = ipPublico;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getUsuarioEspecifico() {
		return usuarioEspecifico;
	}

	public void setUsuarioEspecifico(String usuarioEspecifico) {
		this.usuarioEspecifico = usuarioEspecifico;
	}

	public String getSenhaEspecifica() {
		return senhaEspecifica;
	}

	public void setSenhaEspecifica(String senhaEspecifica) {
		this.senhaEspecifica = senhaEspecifica;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}
	
	
	
}
