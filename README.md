# Projeto de Gerenciamento de Estoque (PostgreSQL + Java)

Este projeto implementa um sistema de gerenciamento de estoque com foco em **grande volume de dados** e **procedimentos armazenados (SPs)** no PostgreSQL.

O objetivo principal é:
- Popular o banco com milhões de registros de forma automatizada usando **Java + Faker**.
- Implementar **Stored Procedures** para controle de estoque.
- Medir o tempo de execução das SPs como requisito da disciplina.

---

## Tecnologias Utilizadas

- Java 21 (OpenJDK)
- Maven para gerenciamento de dependências
- PostgreSQL (rodando em container Docker)
- PgAdmin4 para administração e testes manuais
- JavaFaker para geração de dados fictícios realistas

---

## Estrutura do Projeto

```text
br.com.estoque/
 ├── db/
 │   └── DatabaseConnection.java
 ├── load/
 │   ├── ProductLoader.java
 │   ├── InventoryLoader.java
 │   └── TransactionLoader.java
 ├── Application.java
 └── resources/
     └── application.properties

sql/
 ├── create_database.sql  <-- 1º Executar no pgAdmin 
 ├── tables.sql           <-- 2º Executar no pgAdmin
 └── store-procedures.sql <-- 3º Executar no pgAdmin
```
---

## Configuração do Banco com Docker

    docker run --name estoque-db \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=1234 \
    -e POSTGRES_DB=estoque_db \
    -p 5432:5432 \
    -d postgres:latest
---

## Configuração do Projeto Java

No arquivo application.properties (em src/main/resources/), configure:
```
db.url=jdbc:postgresql://localhost:5432/[NOME_DO_BANCO]
db.user=[USUARIO_CONFIGURADO_NO_POSTGRESQL]
db.password=[SENHA_CONFIGURADA]
```
O programa foi configurado para realizar:
- Inserção de 100.000 produtos
- Inserção de 1.900.000 transações
- Carregamento do estoque inicial

## Resultados Obtidos

### Inserções (via Java)
| Operação                | Tempo    |
|--------------------------|----------|
| 100.000 produtos         | 7,362s   |
| 1.900.000 transações     | 75,899s  |
| Estoque inicial          | 4,338s   |

### Stored Procedures (5 execuções cada)
| Execução    | AddToInventory | SellProduct | GetStockLevel |
|-------------|----------------|-------------|---------------|
| 1ª (lote)   | 7,971s         | 229ms       | 81ms          |
| 2ª          | 100ms          | 172ms       | 97ms          |
| 3ª          | 71ms           | 51ms        | 73ms          |
| 4ª          | 54ms           | 55ms        | 81ms          |
| 5ª          | 70ms           | 53ms        | 64ms          |
