<div align="center">

# 🏗️ Bartz Móveis ERP API

### REST API de Integração (Ponte) para o ERP Legado (IBM DB2)

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![IBM DB2](https://img.shields.io/badge/IBM_DB2-12.1-052FAD?style=for-the-badge&logo=ibm&logoColor=white)](https://www.ibm.com/products/db2)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![API Key](https://img.shields.io/badge/Auth-API_Key-000000?style=for-the-badge&logo=lock&logoColor=white)](#-segurança)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)

</div>

---

## 📸 Preview (Swagger UI)

<div align="center">
  <img src="images/swagger.png" alt="Swagger UI Preview" width="100%">
</div>

---

## 📌 Sobre o Projeto

A **Bartz Móveis ERP API** foi concebida para atuar como uma **Camada Anticorrupção (Ponte)**. Ela resolve o problema de acesso direto a um sistema ERP legado ancorado em um banco de dados **IBM DB2**, o que dificulta a integração com front-ends modernos e expõe credenciais sensíveis.

A API encapsula o acesso nativo e restrito do DB2 entregando serviços RESTful padronizados em JSON, com suporte a paginação e filtros avançados.

Construída com foco em **produção real**, a API incorpora:

- ✅ **Arquitetura em camadas** (Controller → Service → Repository → Model)
- ✅ **Segurança Stateless** via API Key customizada
- ✅ **Camada Anticorrupção** isolando o legado DB2
- ✅ **Containerização completa** com Docker e Docker Compose
- ✅ **Documentação interativa** com Swagger / OpenAPI 3
- ✅ **Suíte de testes** com JUnit 5 e Mockito
- ✅ **Tratamento Global de Erros** para respostas padronizadas

---

## 🏛️ Arquitetura

### Padrão MVC + Service Layer

```mermaid
flowchart TB
  subgraph Application
    A1["ItemController"] --> B1["ItemService"]
    A2["CorController"] --> B2["CorService"]
    B1 --> C1["ItemRepository (JPA)"]
    B2 --> C2["CorRepository (JPA)"]
    C1 --> D["IBM DB2 Database"]
    C2 --> D
  end
```

### Estrutura de Pastas

```
📦 apigetitem
 ├── 🔐 security/            # Filtros de segurança (ApiKeyFilter)
 ├── ⚙️ config/              # Configurações de Segurança e Propriedades
 ├── 📡 controller/          # Endpoints REST (ItemController, CorController)
 ├── 🧩 service/             # Regras de negócio e lógica de aplicação
 ├── 🗄️ repository/          # Interfaces JPA para acesso ao IBM DB2
 ├── 🏷️ model/               # Entidades JPA mapeadas para o banco
 ├── 📤 dto/                 # Data Transfer Objects (ErrorResponse)
 └── ⚠️ exceptions/          # Tratamento global de erros (GlobalExceptionHandler)
```

---

## 🚀 Endpoints da API

### 📦 Itens (`/api/item`)
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| `GET` | `/api/item` | Lista todos (paginado) | ✅ |
| `GET` | `/api/item/find-by-code` | Busca por código exato | ✅ |
| `GET` | `/api/item/find-by-description` | Busca por descrição exata | ✅ |
| `GET` | `/api/item/search-code` | Busca parcial por código | ✅ |
| `GET` | `/api/item/search-desc` | Busca parcial por descrição | ✅ |

### 🎨 Cores (`/api/cor`)
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| `GET` | `/api/cor` | Lista todas (paginado) | ✅ |
| `GET` | `/api/cor/find-by-sigla` | Busca por sigla exata | ✅ |
| `GET` | `/api/cor/find-by-descricao` | Busca por descrição exata | ✅ |
| `GET` | `/api/cor/search-sigla` | Busca parcial por sigla | ✅ |
| `GET` | `/api/cor/search-descricao` | Busca parcial por descrição | ✅ |

---

## 🔐 Segurança

A autenticação é baseada em **API Key (Stateless)**. O cliente deve incluir uma chave secreta em cada requisição via header HTTP.

**Header Obrigatório:**
```
X-API-KEY: <sua_chave_secreta>
```

**Fluxo Interno:**
1. O `ApiKeyFilter` intercepta a requisição.
2. Valida o valor do header contra a propriedade configurada (`api.key`).
3. Se válido, injeta a `Authentication` no `SecurityContextHolder`.
4. Se inválido ou ausente, retorna `401 Unauthorized`.

---

## 🧪 Testes

A API possui cobertura de testes automatizados para garantir a integridade da ponte de dados:

| Camada | Ferramenta | Classes de Teste |
|--------|------------|-----------------|
| **Service (Unit)** | JUnit 5 + Mockito | `BartzErpServiceTest` |
| **Controller (Integration)** | `@WebMvcTest` + MockMvc | `BartzErpControllerTest` |

```bash
# Executar todos os testes
./mvnw test
```

---

## 🐳 Rodando com Docker (Produção)

A forma mais robusta de subir a API em qualquer ambiente:

**1. Configure as variáveis de ambiente:**
Crie um arquivo `.env` na raiz do projeto:

```env
# .env
API_KEY=sua_chave_secreta_aqui
DB_URL=jdbc:db2://seu_host:50000/nomedobanco
DB_USERNAME=usuario_db2
DB_PASSWORD=senha_db2
```

**2. Suba o container:**
```bash
docker-compose up --build -d
```

**3. Verifique o status:**
```bash
docker-compose ps
docker-compose logs -f app
```

---

## 💻 Rodando Localmente (Desenvolvimento)

**Pré-requisitos:**
- Java 17+
- Maven 3.9+
- Acesso ao banco IBM DB2

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd apigetitem

# Execute a aplicação via Maven
./mvnw spring-boot:run
```

---

## 📖 Documentação Interativa (Swagger)

Com a aplicação rodando, acesse:

```
http://localhost:8081/swagger-ui.html
```

Você terá acesso a **todos os endpoints documentados**, com possibilidade de testar as queries para o DB2 diretamente pelo navegador, incluindo o campo para inserir a `X-API-KEY`.

---

## 📊 Estrutura de Dados (DB2)

A API interage com as tabelas principais do ERP:

- **Tabela `ITEM`**: Mapeia `codeItem` (ITEM), `description` (DESCRICAO) e `refComercial` (REF_COMERCIAL).
- **Tabela `COR`**: Mapeia `siglaCor` (SIGLA_COR) e `descricao` (DESCRICAO).

---

## 🛠️ Stack Tecnológica

| Tecnologia | Versão | Finalidade |
|-----------|--------|------------|
| Java | 17 (LTS) | Linguagem principal |
| Spring Boot | 3.4.2 | Framework web e IoC |
| Spring Data JPA | — | ORM e persistência |
| Spring Security | 6.2.x | Controle de acesso (API Key) |
| IBM DB2 | 12.1 | Banco de dados legado |
| SpringDoc OpenAPI | 2.0 | Documentação Swagger |
| Lombok | — | Redução de boilerplate |
| Docker + Compose | — | Containerização |
| JUnit 5 + Mockito | — | Testes automatizados |
| Maven | 3.9+ | Gerenciamento de build |

---

## 🆘 Solução de Problemas

### Erro: `Could not resolve placeholder 'API_KEY'`
- Verifique se o arquivo `.env` está na mesma pasta do JAR ou na raiz do projeto.
- Certifique-se de que a variável `API_KEY` está definida sem espaços extras.

---

## 👨💻 Autor

Desenvolvido por **Roberto Lara** — Full Stack Developer

[![GitHub](https://img.shields.io/badge/GitHub-robertolara-181717?style=for-the-badge&logo=github)](https://github.com/betolara1)

---

<div align="center">

**Bartz Móveis ERP API** — A ponte segura para seus dados legados.

</div>
