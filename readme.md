markdown
# 🎯 CIGS — Central de Comandos Integrados

![Java](https://img.shields.io/badge/Java-21_LTS-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-brightgreen?style=flat-square&logo=springboot)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue?style=flat-square&logo=java)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue?style=flat-square&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.9+-red?style=flat-square&logo=apachemaven)
![License](https://img.shields.io/badge/License-Proprietary-lightgrey?style=flat-square)

> **Migração completa** do CIGS original (Python/Flask/Tkinter) para uma arquitetura moderna baseada em **Java 21 + Spring Boot 3.3.5 + JavaFX**.

Central de gerenciamento e automação de infraestrutura de servidores. Permite cadastrar máquinas, monitorar status em tempo real, agendar atualizações, verificar integridade de bancos Firebird/MSSQL e disparar missões em massa — tudo por uma interface desktop ou via API REST.

---

## 📑 Índice

- [Arquitetura](#-arquitetura)
- [Módulos](#-módulos)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração](#-configuração)
- [Executando](#-executando)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Endpoints da API](#-endpoints-da-api)
- [Testando](#-testando)
- [Roadmap](#-roadmap)
- [Contribuindo](#-contribuindo)
- [Autor](#-autor)
- [Licença](#-licença)

---

## 🏗️ Arquitetura

O projeto segue o padrão **multi-módulo Maven** com separação clara de responsabilidades.
cigs-workspace (pom)
├── cigs-api → API REST central (Spring Boot)
├── cigs-agent → Agente instalado nos servidores monitorados
└── cigs-desktop-javafx → Aplicação desktop (JavaFX + Spring Boot)

text

### Fluxo de comunicação
┌──────────────────────┐ HTTP/REST ┌──────────────────────┐
│ cigs-desktop-javafx │ ───────────────────────► │ cigs-api │
│ (JavaFX) │ │ (Spring Boot REST) │
└──────────────────────┘ └──────────┬───────────┘
│ JDBC
▼
┌──────────────────────┐
│ PostgreSQL │
│ (cigs_db) │
└──────────────────────┘

┌──────────────────────┐ HTTP/REST ┌──────────────────────┐
│ cigs-agent │ ◄─────────────────────── │ cigs-api │
│ (Spring Boot REST) │ │ (Spring Boot REST) │
└──────────┬───────────┘ └──────────────────────┘
│
▼
Firebird / MSSQL
(bancos dos clientes)

text

---

## 📦 Módulos

### 🔹 `cigs-api` — API REST Central

Serviço Spring Boot que concentra as regras de negócio da central.

**Responsabilidades**
- CRUD de servidores (cadastro, listagem, atualização, exclusão)
- Persistência no PostgreSQL via Spring Data JPA
- Expõe endpoints REST para consumo pelo Desktop e pelos Agentes

**Stack:** Spring Boot 3.3.5 · Spring Data JPA · Hibernate · PostgreSQL · Jakarta Validation

---

### 🔹 `cigs-agent` — Agente de Servidor

Aplicação Spring Boot instalada nos servidores monitorados. Recebe comandos da central e executa operações locais.

**Responsabilidades**
- Expor status do servidor (versão, hash, clientes ativos, disco, RAM)
- Agendar tarefas no Windows Task Scheduler
- Baixar e extrair pacotes de atualização (`.rar`)
- Verificar integridade de bancos Firebird e MSSQL
- Sanitizar extrações e limpar logs antigos do CloudUp
- Enviar heartbeat de auto-registro para a central

**Stack:** Spring Boot 3.3.5 · Spring Web · Actuator · Jaybird (Firebird) · mssql-jdbc

---

### 🔹 `cigs-desktop-javafx` — Aplicação Desktop

Interface gráfica para operação do dia a dia.

**Responsabilidades**
- Login com senha mestra
- Visualização de servidores em tabela interativa
- Scan de infraestrutura e monitoramento em tempo real
- Disparo de missões em massa (deploy, atualização, manutenção)
- Dashboard com gráficos e KPIs

**Stack:** JavaFX 21 · Spring Boot (sem web) · Spring Data JPA · BCrypt · OpenCSV

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 (LTS) |
| Build | Maven |
| Framework principal | Spring Boot 3.3.5 |
| GUI Desktop | JavaFX 21 |
| Persistência | Spring Data JPA / Hibernate |
| Banco central | PostgreSQL |
| Bancos dos clientes | Firebird, SQL Server |
| Segurança | Spring Security Crypto, BCrypt |
| Serialização | Jackson |
| Logs | Logback + SLF4J |
| Testes | JUnit 5, Mockito, TestFX |

---

## ✅ Pré-requisitos

- **JDK 21 (LTS)** — [Adoptium](https://adoptium.net/)
- **Maven 3.9+** — [Apache Maven](https://maven.apache.org/download.cgi)
- **PostgreSQL 14+** — banco `cigs_db` (herdado do CIGS em Python)
- **IDE** — Spring Tool Suite (STS), IntelliJ IDEA ou Eclipse com m2e

---

## ⚙️ Configuração

### 1. Clone o repositório

```bash
git clone https://github.com/Biellima2811/CIGS-WorkSpace-JAVA.git
cd CIGS-WorkSpace-JAVA
2. Prepare o banco de dados
Se o banco cigs_db ainda não existe, crie a tabela:

sql
CREATE TABLE servidores (
    id                  SERIAL PRIMARY KEY,
    ip                  VARCHAR(45) UNIQUE NOT NULL,
    hostname            VARCHAR(255),
    ip_publico          VARCHAR(45),
    funcao              VARCHAR(50),
    cliente             VARCHAR(255),
    usuario_especifico  VARCHAR(255),
    senha_especifica    VARCHAR(255),
    criado_em           TIMESTAMP DEFAULT NOW()
);
3. Ajuste as credenciais
Edite cigs-api/src/main/resources/application.yml:

yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cigs_db
    username: postgres
    password: SUA_SENHA_AQUI
⚠️ Nunca versione senhas reais. Use variáveis de ambiente em produção.

🚀 Executando
Build completo do workspace
bash
mvn clean install -DskipTests
Rodar a API
bash
cd cigs-api
mvn spring-boot:run
Disponível em http://localhost:8080

Rodar o Desktop (JavaFX)
bash
cd cigs-desktop-javafx
mvn javafx:run
Rodar o Agente
bash
cd cigs-agent
mvn spring-boot:run
📂 Estrutura do projeto
text
cigs-workspace/
├── pom.xml                              # POM pai (agregador)
│
├── cigs-api/
│   ├── pom.xml
│   └── src/main/
│       ├── java/br/com/fortes/cigs/api/
│       │   ├── CigsApiApplication.java
│       │   └── server/
│       │       ├── Servidor.java              # Entidade JPA
│       │       ├── ServidorDTO.java           # DTO de resposta
│       │       ├── ServidorFormDTO.java       # DTO de entrada
│       │       ├── ServidorRepository.java    # Repositório JPA
│       │       ├── ServidorService.java       # Regras de negócio
│       │       └── ServidorController.java    # Endpoints REST
│       └── resources/
│           └── application.yml
│
├── cigs-agent/
│   ├── pom.xml
│   └── src/main/java/br/com/fortes/cigs/agent/
│       ├── AgentApplication.java
│       ├── config/
│       ├── controller/
│       ├── service/
│       └── util/
│
└── cigs-desktop-javafx/
    ├── pom.xml
    └── src/main/
        ├── java/br/com/fortes/cigs/desktop/
        │   ├── CentralApplication.java
        │   ├── ui/controller/
        │   ├── ui/dialog/
        │   └── service/
        └── resources/
            ├── fxml/
            ├── css/
            └── application.yml
🔌 Endpoints da API
Servidores — /api/v1/servers
Método	Rota	Descrição	Sucesso	Erro
GET	/api/v1/servers	Lista todos os servidores	200	—
GET	/api/v1/servers/{ip}	Busca servidor pelo IP	200	404
POST	/api/v1/servers	Cadastra novo servidor	201	400 / 409
PUT	/api/v1/servers/{id}	Atualiza servidor por ID	200	400 / 404
DELETE	/api/v1/servers/{id}	Remove servidor por ID	204	404
Agente — /cigs/*
Método	Rota	Descrição
GET	/cigs/status	Status do agente (versão, hash, clientes, disco, RAM)
POST	/cigs/executar	Agenda execução de script no servidor
POST	/cigs/check_db	Verifica integridade do banco (Firebird/MSSQL)
GET	/cigs/relatorio	Retorna relatório de execuções
POST	/cigs/abortar	Cancela tarefas em execução
POST	/cigs/descomentar	Descomenta clientes no config.ini
POST	/cigs/limpar_logs	Remove logs antigos do CloudUp
POST	/cigs/register	Auto-registro de novo agente na central
🧪 Testando
Após subir o cigs-api, teste com curl ou pelo navegador.

Listar todos os servidores
bash
curl http://localhost:8080/api/v1/servers
Buscar por IP
bash
curl http://localhost:8080/api/v1/servers/10.100.104.19
Cadastrar novo servidor
bash
curl -X POST http://localhost:8080/api/v1/servers \
  -H "Content-Type: application/json" \
  -d '{
    "ip": "192.168.1.10",
    "hostname": "SRV-TESTE",
    "funcao": "App",
    "cliente": "Cliente Demo"
  }'
Atualizar servidor
bash
curl -X PUT http://localhost:8080/api/v1/servers/1 \
  -H "Content-Type: application/json" \
  -d '{"ip": "192.168.1.11", "hostname": "SRV-ATUALIZADO"}'
Remover servidor
bash
curl -X DELETE http://localhost:8080/api/v1/servers/1
💡 No navegador: http://localhost:8080/api/v1/servers já mostra o JSON formatado.

🗺️ Roadmap
Fase	Descrição	Status
1	Fundação e setup multi-módulo Maven	✅ Concluído
2	Migração do agente para Spring Boot REST	✅ Concluído
3	Central: banco, segurança e login	✅ Concluído
4	Central: infraestrutura + cliente HTTP	🚧 Em andamento
5	Renomear aba + novo Dashboard com KPIs	⏳ Pendente
6	Tela de configuração de banco + limpezas	⏳ Pendente
7	Homologação e entrega	⏳ Pendente
🤝 Contribuindo
Faça um fork do projeto

Crie uma branch: git checkout -b feature/nova-funcionalidade

Commit: git commit -m 'Adiciona nova funcionalidade'

Push: git push origin feature/nova-funcionalidade

Abra um Pull Request

👤 Autor
Gabriel Levi

GitHub: @Biellima2811

Empresa: Fortes Tecnologia

📄 Licença
Projeto de uso interno e proprietário da Fortes Tecnologia.
Todos os direitos reservados © 2026.

<p align="center"> <strong>CIGS — Central de Comandos Integrados</strong><br> Versão 4.0 · Java Edition · © 2026 Fortes Tecnologia </p> ```