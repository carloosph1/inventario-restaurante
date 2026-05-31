
# Sistema de Inventário para Restaurante

API REST para gerenciamento de estoque de restaurante, desenvolvida com Spring Boot.

## Tecnologias

- Java 17
- Spring Boot 3.2
- Spring Data JPA / Hibernate
- H2 Database (desenvolvimento)
- Bean Validation
- Lombok
- Swagger / SpringDoc OpenAPI

##  Arquitetura

O projeto segue o padrão MVC com 4 camadas:

### Entidades
- **Categoria** — classifica os produtos (ex: Laticínios, Carnes)
- **Fornecedor** — quem fornece os produtos
- **Produto** — itens do estoque com quantidade e preço
- **MovimentoEstoque** — registra entradas e saídas do estoque

##  Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+

### Instalação
```bash
git clone https://github.com/seuusuario/sistema-de-inventario.git
cd sistema-de-inventario
```

### Executar
```bash
./mvnw spring-boot:run
```

### URLs úteis
| Serviço | URL |
|---------|-----|
| API | http://localhost:8080 |
| Swagger | http://localhost:8080/swagger-ui.html |
| H2 Console | http://localhost:8080/h2-console |

##  Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /api/v1/categorias | Lista todas as categorias |
| GET | /api/v1/categorias/{id} | Busca categoria por ID |
| POST | /api/v1/categorias | Cria nova categoria |
| DELETE | /api/v1/categorias/{id} | Remove categoria |
| GET | /api/v1/fornecedores | Lista todos os fornecedores |
| GET | /api/v1/fornecedores/{id} | Busca fornecedor por ID |
| POST | /api/v1/fornecedores | Cria novo fornecedor |
| GET | /api/v1/produtos | Lista todos os produtos |
| GET | /api/v1/produtos/{id} | Busca produto por ID |
| POST | /api/v1/produtos | Cria novo produto |
| PUT | /api/v1/produtos/{id} | Atualiza produto |
| DELETE | /api/v1/produtos/{id} | Remove produto |
| GET | /api/v1/movimentos | Lista movimentos de estoque |
| POST | /api/v1/movimentos | Registra entrada ou saída |

##  Exemplos de uso

### Criar categoria

```bash
curl -X POST http://localhost:8080/api/v1/categorias \
  -H "Content-Type: application/json" \
  -d '{"nome": "Laticínios", "descricao": "Leite, queijo, manteiga"}'
```

### Criar produto
```bash
curl -X POST http://localhost:8080/api/v1/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Queijo Mussarela",
    "quantidade": 10.0,
    "unidade": "kg",
    "preco": 35.90,
    "categoria": {"id": 1},
    "fornecedor": {"id": 1}
  }'
```

### Registrar entrada no estoque
```bash
curl -X POST http://localhost:8080/api/v1/movimentos \
  -H "Content-Type: application/json" \
  -d '{
    "produto": {"id": 1},
    "tipo": "ENTRADA",
    "quantidade": 5.0,
    "observacao": "Recebimento semanal"
  }'
```

### Registrar saída do estoque
```bash
curl -X POST http://localhost:8080/api/v1/movimentos \
  -H "Content-Type: application/json" \
  -d '{
    "produto": {"id": 1},
    "tipo": "SAIDA",
    "quantidade": 2.0,
    "observacao": "Uso na cozinha"
  }'
```

## Autores

- Carlos Henrique - 2026196427, Kauane Samara e Igor Guilherme — [@carloosph1](https://github.com/carloosph1/inventario-restaurante)

## Como rodar os testes

```bash
./mvnw test
```