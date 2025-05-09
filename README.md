# XPTO Finance Control

**XPTO Finance Control** é uma aplicação RESTful desenvolvida em Java com Spring Boot, voltada para o gerenciamento de clientes (Pessoa Física e Jurídica), contas bancárias, endereços e movimentações financeiras. A aplicação também fornece relatórios e cálculos de receitas, com regras de negócio implementadas tanto na aplicação quanto diretamente no banco de dados Oracle (triggers e procedures), garantindo integridade e segurança dos dados.

---

## 🛠️ Tecnologias Utilizadas

- Java 24
- Spring Boot 3.4.5
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter JDBC
  - Spring Boot Starter Test
- Oracle JDBC Driver (ojdbc11)
- SpringDoc OpenAPI (Swagger)
- Lombok
- Jackson (para serialização/desserialização de JSON)
- Oracle Database (com suporte a triggers e procedures)

---

## ⚙️ Funcionalidades

### 1. Clientes
- **Pessoa Física:**
  - Cadastro, listagem, atualização e exclusão
- **Pessoa Jurídica:**
  - Cadastro, listagem, atualização e exclusão

### 2. Contas
- Cadastro de contas vinculadas a clientes
- Atualização (restrita a contas sem movimentações)
- Exclusão lógica (inativação)

### 3. Endereços
- Cadastro, listagem, atualização e exclusão

### 4. Movimentações Financeiras
- Registro de movimentações (crédito e débito)
- Filtros por período ou por conta

### 5. Relatórios
- Relatórios de clientes e movimentações financeiras

### 6. Cálculo de Receita
- Cálculo automático com base nas movimentações registradas

### 7. Regras de Negócio no Banco de Dados
- **Procedure:** Inativa contas com movimentações ou exclui fisicamente contas sem movimentações
- **Triggers:** Impedem alteração de campos sensíveis como CPF, CNPJ e data de cadastro

---

## ✅ Boas Práticas Aplicadas

- Arquitetura em camadas (Controller, Service, Repository, Model)
- Uso de DTOs para transporte e validação de dados
- Validações com Jakarta Bean Validation (`@NotNull`, `@Size`, etc.)
- Exclusão lógica para manter histórico dos dados
- Triggers e procedures no banco para garantir regras de integridade
- Endpoints REST bem definidos, utilizando verbos HTTP apropriados
- Redução de boilerplate com Lombok (`@Data`, `@Builder`, etc.)
- Código reutilizável com uso de métodos auxiliares

---

## 📌 Endpoints e Documentação da API

A documentação completa da API, incluindo todos os endpoints disponíveis, parâmetros, exemplos e respostas, está disponível via Swagger UI.

### 📘 Acesse a documentação interativa:

```
http://localhost:8080/swagger-ui.html
```

> 💡 Utilize essa interface para explorar, testar e entender todos os recursos da API de forma prática.

---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos

- Java 24 instalado
- Oracle Database configurado e em execução
- Maven instalado e configurado no PATH
- Oracle JDBC Driver (`ojdbc11`) instalado localmente ou via repositório Maven

### ⚙️ Configuração do Banco de Dados

1. As tabelas serão criadas automaticamente ao inicializar a aplicação.
1. Execute os scripts SQL fornecidos na pasta `/scripts` para criar as triggers e procedures necessárias.
2. Atualize o `application.properties` com suas credenciais e URL do Oracle Database:

```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/xe
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.hibernate.ddl-auto=none
```

### ▶️ Rodar a Aplicação

No terminal, na raiz do projeto, execute:

```bash
mvn spring-boot:run
```
