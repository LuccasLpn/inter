# Projeto - API de Gestão de Usuários e Carteiras

## Descrição
Este projeto é uma API desenvolvida para gerenciar usuários, carteiras digitais (wallets) e limites de transação (transaction limits). Quando um novo usuário é criado, sua carteira e os limites de transação são gerados automaticamente.

## Tecnologias Utilizadas
- **Linguagem:** Java
- **JDK:** 17
- **Framework:** Spring Boot 3
- **Banco de Dados:** MySQL
- **Containerização:** Docker
- **Cache:** Redis
- **Resiliência:** **Circuit Breaker** para integração de cotação de moedas

## Configuração e Execução

### Pré-requisitos
Antes de executar o projeto, certifique-se de ter instalado:
- **JDK 17**
- **Docker e Docker Compose**
- **MySQL**

### Passos para execução

1. Clone este repositório:
   ```sh
   git clone https://github.com/LuccasLpn/inter
   cd inter
   ```

2. Configure o banco de dados no MySQL com as credenciais apropriadas.

3. Execute o Docker para subir os serviços necessários:
   ```sh
   docker-compose up -d
   ```

4. Compile e rode a aplicação Spring Boot:
   ```sh
   ./mvnw spring-boot:run
   ```
   Ou utilizando o JAR gerado:
   ```sh
   java -jar target/seu-projeto.jar
   ```

## Estrutura do Banco de Dados

### Tabelas

```sql
CREATE TABLE exchange_rates
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    uuid       BINARY(16)            NOT NULL,
    rate       DECIMAL               NOT NULL,
    created_at datetime              NOT NULL,
    CONSTRAINT pk_exchange_rates PRIMARY KEY (id)
);

CREATE TABLE transaction_limits
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    uuid        BINARY(16)            NOT NULL,
    user_id     BIGINT                NOT NULL,
    daily_limit DECIMAL(15, 2)        NOT NULL,
    used_today  DECIMAL(15, 2)        NOT NULL,
    last_reset  date                  NOT NULL,
    created_at  datetime              NOT NULL,
    CONSTRAINT pk_transaction_limits PRIMARY KEY (id)
);

CREATE TABLE transactions
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    uuid             BINARY(16)            NOT NULL,
    from_wallet_id   BIGINT                NOT NULL,
    to_wallet_id     BIGINT                NOT NULL,
    amount           DECIMAL               NOT NULL,
    transaction_type VARCHAR(255)          NOT NULL,
    exchange_rate_id BIGINT                NULL,
    created_at       datetime              NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    uuid       BINARY(16)            NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    document   VARCHAR(255)          NOT NULL,
    type       VARCHAR(255)          NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    created_at datetime              NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE wallets
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    uuid         BINARY(16)            NOT NULL,
    user_id      BIGINT                NOT NULL,
    balance      DECIMAL               NOT NULL,
    balance_type VARCHAR(255)          NOT NULL,
    created_at   datetime              NOT NULL,
    CONSTRAINT pk_wallets PRIMARY KEY (id)
);

ALTER TABLE exchange_rates
    ADD CONSTRAINT uc_exchange_rates_uuid UNIQUE (uuid);

ALTER TABLE transaction_limits
    ADD CONSTRAINT uc_transaction_limits_user UNIQUE (user_id);

ALTER TABLE transaction_limits
    ADD CONSTRAINT uc_transaction_limits_uuid UNIQUE (uuid);

ALTER TABLE transactions
    ADD CONSTRAINT uc_transactions_uuid UNIQUE (uuid);

ALTER TABLE users
    ADD CONSTRAINT uc_users_document UNIQUE (document);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_uuid UNIQUE (uuid);

ALTER TABLE wallets
    ADD CONSTRAINT uc_wallets_uuid UNIQUE (uuid);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_EXCHANGE_RATE FOREIGN KEY (exchange_rate_id) REFERENCES exchange_rates (id);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_FROM_WALLET FOREIGN KEY (from_wallet_id) REFERENCES wallets (id);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_TO_WALLET FOREIGN KEY (to_wallet_id) REFERENCES wallets (id);

ALTER TABLE transaction_limits
    ADD CONSTRAINT FK_TRANSACTION_LIMITS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE wallets
    ADD CONSTRAINT FK_WALLETS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
```

![Descrição da imagem](https://cdn.discordapp.com/attachments/827795637113454615/1343037824668274810/image.png?ex=67bbd0f1&is=67ba7f71&hm=f6c08928454430b9308cf7b13a79d0b2f5570f025500999e36b7b506a4c3c00e&)

## Endpoints

### Criar Usuário
Este endpoint cria um novo usuário e automaticamente gera uma carteira e define limites de transação.

#### **Requisição:**
```sh
curl --location 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Luccas Pereira Nunes",
    "email": "luccas.lpn@outlook.com",
    "document": "48188562823",
    "password": "123456789"
}'
```

#### **Resposta Esperada:**
```json
{
    "id": 1,
    "name": "Luccas Pereira Nunes",
    "email": "luccas.lpn@outlook.com",
    "document": "48188562823"
}
```

### Adicionar Saldo à Carteira
Este endpoint adiciona saldo a um usuário específico com base no ID da carteira.

#### **Requisição:**
```sh
curl --location 'http://localhost:8080/wallet/1facec34-d88a-4422-9342-6a4d7b9dadfc/balance' \
--header 'Content-Type: application/json' \
--data '{
    "balance": 1000,
    "currency": "BRL"
}'
```

#### **Resposta Esperada:**
```json
{
  "balance": 1000,
  "currency": "BRL"
}
```

### Realizar Transferência ou Conversão
Este endpoint permite realizar transferências entre carteiras ou conversões de moeda.

#### **Requisição:**
```sh
curl --location 'http://localhost:8080/transactions/remittance' \
--header 'Content-Type: application/json' \
--data '{
    "fromWalletUuid": "49b02cff-0a6c-4b0e-a0a3-6f16bdcda54d",
    "toWalletUuid": "2b2f1154-ac45-46dc-9339-8b270d17f6a4",
    "amount": 100,
    "transactionType": "TRANSFER"
}'
```

#### **Resposta Esperada:**
```json
{
    "transactionId": "abc12345-def6-7890-ghij-klmnopqrstuv",
    "fromWalletUuid": "49b02cff-0a6c-4b0e-a0a3-6f16bdcda54d",
    "toWalletUuid": "2b2f1154-ac45-46dc-9339-8b270d17f6a4",
    "amount": 100,
    "transactionType": "TRANSFER",
    "status": "COMPLETED"
}
```

### Mecanismo de Cache e Resiliência
A API utiliza **Redis Cache** para armazenar temporariamente a última cotação de moeda recuperada. Isso permite que, caso o serviço de cotação esteja indisponível, a API possa **retornar um valor recente armazenado**, garantindo a continuidade do funcionamento.

Além disso, foi implementado um **Circuit Breaker** para gerenciar falhas na integração de cotação de moedas. Esse mecanismo **monitora a comunicação com o serviço externo** e, caso identifique falhas consecutivas, **interrompe temporariamente as requisições** para evitar sobrecarga e degradação da API.

### Job de Limpeza do Cache
Para garantir que os dados armazenados no cache estejam sempre atualizados e evitar o uso de cotações antigas, foi implementado um **job agendado** que **limpa o cache toda segunda-feira à meia-noite**.

Este job utiliza a funcionalidade do **Spring Scheduler** e executa a limpeza do cache automaticamente, garantindo que a API sempre consulte novas cotações ao longo da semana. Isso evita que usuários utilizem dados desatualizados e mantém a precisão das informações financeiras.

### Job de Reset dos Limites Diários
A API conta com um **job agendado para resetar os limites diários de transação**. Esse job é executado automaticamente **todos os dias à meia-noite** e tem como função garantir que os limites diários das carteiras dos usuários sejam redefinidos para permitir novas transações no dia seguinte.

Esse processo ocorre por meio do **Spring Scheduler**, garantindo que, a cada dia, os usuários possam realizar transações respeitando as regras de limite estabelecidas para cada conta.

#### Benefícios do **Job de Reset dos Limites Diários**:
- **Automatização da gestão de limites**: Não há necessidade de intervenção manual para redefinir os limites a cada dia.
- **Garantia de conformidade com regras de negócio**: Mantém o controle adequado das transações permitidas diariamente.
- **Melhoria na experiência do usuário**: Evita inconsistências e permite que os usuários continuem suas transações sem problemas.

Caso ocorra alguma falha no reset dos limites, a API pode ser configurada para **logar erros e alertar administradores**, garantindo que qualquer problema seja tratado rapidamente.

## Considerações Finais
Este projeto foi desenvolvido como parte do processo seletivo para o Inter. A API está estruturada seguindo boas práticas de desenvolvimento, incluindo arquitetura modular, princípios SOLID e integração com tecnologias modernas como Redis para caching e Docker para facilitar a implantação.

Caso tenha dúvidas ou sugestões, fique à vontade para contribuir!

