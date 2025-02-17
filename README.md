# Documentação da API - E-commerce

## Visão Geral
Esta API permite gerenciar usuários, produtos, categorias e pedidos de um sistema de e-commerce.

## Tecnologias Utilizadas
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Banco de dados PostgreSQL](https://www.postgresql.org/)

---
## Práticas adotadas

- SOLID, DRY, YAGNI, KISS
- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Uso de exceptions personalizadas para respostas padronizadas e claras.
- Geração automática do Swagger com a OpenAPI 3

[//]: # (- Teste Unitários com o framework JUnit 5, com o suporte do Mockito para simulação de dependências)

---
## Autenticação e Autorização
A API utiliza Spring Security junto com a dependência `spring-boot-starter-oauth2-resource-server` para 
gerenciar a autenticação via JWT (JSON Web Token). A autenticação é realizada através 
de um token JWT, que deve ser enviado no cabeçalho das requisições.

---

## Controle de Acesso por Roles 
Os usuários são categorizados com diferentes Roles:
- `BASIC`: pode acessar apenas endpoints essenciais, como consulta de produtos e criação de pedidos.
- `ADMIN`: tem acesso a operações administrativas, como gestão de produtos, categorias e usuários.

### Exemplo de configuração de segurança no Spring Security:
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  return http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
                  .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                  .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                  .requestMatchers(HttpMethod.POST, "/products/create").hasAuthority("SCOPE_" + ADMIN.name())
                  .requestMatchers(HttpMethod.POST, "/category").hasAuthority("SCOPE_" + ADMIN.name())
                  .anyRequest().authenticated())
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .httpBasic(httpBasic -> httpBasic.disable())
          .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
          .build();
}

```

---

## Endpoints

### Autenticação
- **Método:** `POST /auth`
- **Descrição:** Para fazer o login
- **Request Body:**
  ```json
  {
    "username": "admin",
    "password": "admin"
  }
- **Response**: `200 ok`.
  ```json
   {
      "token": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktand0Iiwic3ViIjoiYWRtaW4iLCJleHAiOjE3Mzk1ODI2MjksImlhdCI6MTczOTU3OTAyOSwic2NvcGUiOiJBRE1JTiBCQVNJQyJ9.byRkq4fgJagUdUjp3dPT9UuwI1SYaDcW8CH65dmLD67FV0C6Up3eNxyQCGEOxDwtLvfwg7KGYLX3yySGj-f7U21qAzwcKaTr1IRepxiP9kg-d3EotwcEcT4kwyMdvHIlgStPsEuSzX58TS3YXzBgs06YJxG2dM-wQ2mJ-9rrYsWsxt07YZySwCj-HPaPzPjHsZEwDgw_NtmMuqA8t7EXsg9wolIh7y_sJWN4q1YeP7KJyMfBzZAKXdVph-66yvHzCN6yJtah0CmC2bYQ1JMl8CyiGkaXQZ7Xaa-89cWu9LFS3ZIrZvnFq6tozHqk-Wj2N4mx88GaeZsohMBimZrzSg"
   }
  ```

---

### 1. Usuários (`/user`)

### Cadastrar Usuário
- **Método:** `POST /user`
- **Descrição:** Cria um novo usuário.
- **Request Body:**
  ```json
  {
    "username": "john_doe",
    "email": "john@example.com",
    "password": "senha123"
  }
- **Response**: `201 Created` (Com localização do recurso criado no header).

### Consultar Usuário
- **Método:** `GET /user/{id}`
- **Descrição:** Obtém os dados de um usuário pelo ID.
- **Response:**
  ```json
  {
  "userId": "25fcd6c2-69c4-48d4-9a30-7675a2682075",
  "username": "mario",
  "email": "mario@gmail.com",
  "roles": [
    {
      "id": 2,
      "name": "BASIC"
    }
  ],
  "address": {
     "state": "Amazonas",
     "city": "Manaus",
     "street": "3"
  },
  "phone": "9002-8922",
  "orders": [
    {
      "orderId": "4ada1c41-c3e2-41d0-9f86-068344ae9372",
      "orderDate": "2025-02-14T20:22:55.507964",
      "totalAmount": 26980.0,
      "status": "PENDING"
    },
    {
      "orderId": "da39f35d-aa2a-4fd0-b904-fc6e79a0f6d3",
      "orderDate": "2025-02-11T05:31:57.000048",
      "totalAmount": 19990.0,
      "status": "PENDING"
    }
  ]
}
  
### Adicionar Endereço
- **Método:** `POST /user/{username}/address`
- **Descrição:** Adiciona um endereço ao usuário.
- **Request Body:**
  ```json
  {
    "street": "Rua das Flores",
    "city": "São Paulo",
    "state": "SP"
  }
- **Status Http**: `200 ok`
---
### 2. Produtos (`/products`)

### Listar Produtos
- **Método:** `GET /products`
- **Descrição:** Retorna todos os produtos cadastrados.
- **Response:**
  ```json
  [
    {
        "id": 1,
        "name": "Produto A",
        "price": 99.99,
        "category": "Eletrônicos"
    }
  ]

- **Status Http**: `200 ok`

### Criar Produto
- **Método:** `POST /products`
- **Descrição:** Cria um novo produto.
- **Request Body:**
  ```json
  {
    "name": "Produto A",
    "price": 99.99,
    "categoryId": 1
  }
- **Response**:
  ```json
  {
    "id": 1,
    "name": "Produto A",
    "price": 99.99,
    "category": "Eletrônicos"
  } 

### Atualizar Produto
- **Método:** `PUT /products/{id}`
- **Descrição:** Atualiza um produto existente.
- **Request Body:**
  ```json
  {
    "name": "Produto Atualizado",
    "price": 89.99
  }
- **Response**:
  ```json
  {
    "id": 1,
    "name": "Produto Atualizado",
    "price": 89.99
  } 
### Excluir Produto
- **Método:** `DELETE /products/{id}`
- **Descrição:** Remove um produto pelo ID.
- **Response:** `204 No Content`

### Listar Categorias
- **Método:** `GET /products/category`
- **Descrição:** Retorna todas as categorias disponíveis.
- **Response:**
  ```json
  [
    {
      "id": 1,
      "name": "Eletrônicos"
    },
    {
      "id": 2,
      "name": "Esportes"
    },
    {
      "id": 3,
      "name": "Livros"
    }
  ]
---
### 3. Pedidos (`/order`)

### Criar Pedido
- **Método:** `POST /order/{userId}`
- **Descrição:** Retorna todos os produtos cadastrados.
- **Request Body:**
  ```json
    {
    "products": [
      {
        "productId": 6,
        "quantity": 10
      },
      {
        "productId": 3,
        "quantity": 5
      },
      {
        "productId": 1,
        "quantity": 5
      }
     ]
    }


- **Response**:
    ```json
    {
	  "username": "felipe",
	  "orderId": "10c66e34-6b63-4fdd-9beb-fcf4d0c96aa9",
	  "orderDate": "2025-01-29T00:06:54.041811",
	  "productsProcessed": [
		{
		  "productId": 6,
		  "name": "Camisa do Flameng",
		  "quantity": 10,
		   "price": 600.00
		},
		{
		  "productId": 3,
	      "name": "Bola de Futebool",
		  "quantity": 5,
		  "price": 120.00
		},
		{
		  "productId": 1,
		  "name": "Carregador tipo C",
		  "quantity": 5,
		  "price": 99.99
		}
	  ],
	  "totalAmount": 7099.95,
	  "status": "PENDING"
    }

---

# Todos os Endpoints

!["imagem swagger - openAPI ](src/image/img.png)

