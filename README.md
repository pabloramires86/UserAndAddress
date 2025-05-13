# 📌 API de Usuários e Validação de Endereço

Esta API permite o gerenciamento de usuários, incluindo criação, atualização e exclusão, além da autenticação via JWT.

---

## 🚀 **Tecnologias Utilizadas**
- ⚙️ **Spring Boot** — Framework principal
- 🔒 **Spring Security & JWT** — Autenticação segura
- 🗄 **PostgreSQL** — Banco de dados
- 🔧 **Mockito & JUnit 5** — Testes unitários

---

## 1 Configure o banco de dados (application.properties)
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha


## 2 Execute a aplicação
mvn spring-boot:run


## 📌 Agora sua API está rodando!

### 📜 Endpoints
#### 🔹 Autenticação (AuthController)
| Método | Rota | Descrição  
| POST | /auth/login | Autentica um usuário e retorna um token  
| GET | /auth/all | Requer USER ou ADMIN para acessar dados  
| GET | /auth/admin | Requer ADMIN para acessar informações administrativas  
| GET | /auth/user | Retorna uma lista paginada de usuários 


### Exemplo de requisição POST /auth/login
{
"username": "pablo",
"password": "teste123",
"role": "ROLE_ADMIN"
}


Resposta (200 OK)
"fake-jwt-token"



#### 🔹 Gerenciamento de Usuários (UserController)
| Método | Rota | Descrição  
| GET | /users/{id} | Retorna informações de um usuário pelo ID  
| POST | /users/create | Cria um novo usuário  
| PUT | /users/{id} | Atualiza os dados de um usuário  
| DELETE | /users/{id} | Remove um usuário  
| GET | /users/search | Pesquisa usuários por nome, email ou período


### Exemplo de requisição POST /users/create
{
"name": "Novo Usuário",
"email": "novo@email.com",
"username": "novoUser",
"password": "senha123",
"role": "ROLE_USER"
}


Resposta (200 OK)
{
"name": "Novo Usuário",
"email": "novo@email.com",
"username": "novoUser",
"role": "ROLE_USER"
}


