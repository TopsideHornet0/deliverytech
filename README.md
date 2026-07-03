# 📦 DeliveryTech API

Backend REST desenvolvido com **Java 21** e **Spring Boot 3**, simulando o funcionamento de plataformas modernas de delivery como **iFood**, **Uber Eats** e **Rappi**.

O objetivo deste projeto é reproduzir o funcionamento de uma plataforma completa de delivery, aplicando conceitos modernos de desenvolvimento Backend, arquitetura em camadas, segurança, documentação, testes automatizados, monitoramento e containerização.

---

# 🚀 Visão Geral

<p align="center">
    <img src="assets/banner-api.png" alt="Banner DeliveryTech" width="100%">
</p>

<p align="center">

Java 21 • Spring Boot 3 • Spring Security • JWT • Docker • Swagger • Maven • JPA • H2 Database

</p>

---

# 📖 Objetivos do Projeto

O DeliveryTech foi desenvolvido como projeto de estudo e portfólio, buscando aproximar o máximo possível uma aplicação do ambiente encontrado em empresas.

Durante seu desenvolvimento foram aplicados conhecimentos relacionados a:

- Desenvolvimento de APIs REST
- Arquitetura em Camadas
- Segurança utilizando Spring Security
- Autenticação com JWT
- Persistência de dados com Spring Data JPA
- Documentação automática com Swagger/OpenAPI
- Monitoramento da aplicação
- Testes automatizados
- Containerização utilizando Docker
- Organização de projetos Backend seguindo boas práticas do mercado.

Além da implementação técnica, o projeto também foi utilizado como laboratório para aprendizado contínuo, evolução da arquitetura e aperfeiçoamento das práticas de desenvolvimento.

---

# 🤖 Utilização de Inteligência Artificial

Durante o desenvolvimento deste projeto foram utilizadas ferramentas de Inteligência Artificial como apoio técnico.

A IA foi empregada principalmente para:

- Revisão de arquitetura;
- Explicação de conceitos do ecossistema Spring;
- Organização da documentação;
- Apoio na escrita de testes automatizados;
- Discussões sobre melhorias futuras da aplicação.

Todo o código implementado foi estudado, compreendido, adaptado e validado antes de sua utilização, utilizando a IA como ferramenta de apoio ao desenvolvimento e aprendizado, e não como substituição do conhecimento técnico.

---

# 🏗 Arquitetura da Aplicação

O projeto segue uma arquitetura em camadas, separando cada responsabilidade da aplicação para facilitar manutenção, escalabilidade e evolução do sistema.

<p align="center">
    <img src="assets/arquitetura-api.png" alt="Arquitetura da API" width="100%">
</p>

Cada camada possui uma responsabilidade específica.

### Controller

Recebe as requisições HTTP e devolve as respostas da API.

### Service

Contém toda a regra de negócio da aplicação.

### Repository

Responsável pelo acesso aos dados utilizando Spring Data JPA.

### Database

Responsável pela persistência das informações.

---

# 🔄 Fluxo de Funcionamento da API

O diagrama abaixo representa o fluxo percorrido por uma requisição desde o cliente até o banco de dados.

<p align="center">
    <img src="assets/fluxo-api.png" alt="Fluxo da API" width="100%">
</p>

Esse fluxo demonstra como cada camada participa do processamento de uma requisição, evidenciando a separação de responsabilidades adotada durante o desenvolvimento.

---

# 🚀 Funcionalidades

## 🔐 Autenticação e Segurança

- Cadastro de usuários
- Login utilizando JWT
- Geração automática de Token JWT
- Criptografia de senhas com BCrypt
- Controle de acesso baseado em Roles
- Spring Security
- Autorização baseada em perfis

---

## 👤 Gestão de Clientes

- Cadastro
- Consulta
- Atualização
- Exclusão lógica
- Validação de dados

---

## 🍽 Gestão de Restaurantes

- Cadastro
- Consulta
- Atualização
- Exclusão lógica
- Controle de disponibilidade
- Taxa de entrega
- Tempo médio de entrega

---

## 🍔 Gestão de Produtos

- Cadastro
- Atualização
- Exclusão
- Associação entre restaurante e produto
- Consulta de produtos por restaurante

---

## 🛒 Gestão de Pedidos

- Criação de pedidos
- Associação de múltiplos produtos
- Cálculo automático do valor total
- Atualização do status do pedido
- Fluxo completo do pedido

---

## ⚡ Cache

- Spring Cache

---

## 📄 Documentação

- Swagger / OpenAPI

---

## 📈 Monitoramento

- Spring Boot Actuator

---

## 🧪 Testes Automatizados

O projeto possui uma suíte de testes composta por:

- Testes Unitários
- Testes de Integração
- Testes de Segurança
- MockMvc
- Mockito
- Spring Boot Test
- JUnit 5

Todos os testes podem ser executados utilizando Maven.

---

# 📂 Organização do Projeto

```text
src
├── main
│   ├── java
│   │   └── com.deliverytech
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── repository
│   │       ├── security
│   │       ├── service
│   │       ├── validation
│   │       └── DeliveryTechApiApplication
│   │
│   └── resources
│       ├── application.properties
│       └── data.sql
│
└── test
    ├── controller
    ├── security
    ├── service
    └── repository
```

Essa estrutura facilita a manutenção do projeto e permite sua evolução de maneira organizada.

---

# 🧪 Stack Tecnológica

| Categoria | Tecnologia |
|------------|------------|
| Linguagem | Java 21 |
| Framework | Spring Boot 3 |
| Backend REST | Spring Web |
| Persistência | Spring Data JPA |
| Segurança | Spring Security |
| Autenticação | JWT |
| Criptografia | BCrypt |
| Banco de Dados | H2 Database |
| Cache | Spring Cache |
| Documentação | Swagger / OpenAPI |
| Monitoramento | Spring Boot Actuator |
| Testes | JUnit 5, Mockito, MockMvc |
| Containerização | Docker |
| Orquestração | Docker Compose |
| Build | Maven |

---

# 🗄 Banco de Dados

Durante o desenvolvimento foi utilizado o **H2 Database**, permitindo que qualquer pessoa execute o projeto rapidamente sem necessidade de instalar um banco externo.

Sempre que a aplicação é iniciada, o Spring executa automaticamente o arquivo **data.sql**, responsável por popular o banco com dados iniciais para demonstração.

Dessa forma, a aplicação já inicia pronta para utilização e testes.

Também é possível acessar o console H2 durante o desenvolvimento.

Console:

```
http://localhost:8080/h2-console
```

---

# ⚙️ Como Executar o Projeto

## Pré-requisitos

- Java 21
- Maven
- Docker Desktop (opcional)

---

## Executando Localmente

Clone o projeto

```bash
git clone https://github.com/TopsideHornet0/deliverytech.git
```

Entre na pasta

```bash
cd deliverytech
```

Compile o projeto

```bash
mvn clean install
```

Execute a aplicação

```bash
mvn spring-boot:run
```

A API ficará disponível em

```
http://localhost:8080
```

---

## Executando com Docker

Construa a imagem

```bash
docker compose build
```

Execute os containers

```bash
docker compose up
```

Ou diretamente

```bash
docker compose up --build
```

Após a inicialização, a aplicação estará disponível em

```
http://localhost:8080
```

---

# 📄 Documentação da API

Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

# 📌 Principais Endpoints

| Método | Endpoint |
|---------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |
| GET | /api/clientes |
| GET | /api/restaurantes |
| GET | /api/produtos |
| POST | /api/pedidos |

---

# 🚧 Roadmap

## Infraestrutura

- PostgreSQL
- Redis
- RabbitMQ
- Flyway
- Testcontainers

## Observabilidade

- Prometheus
- Grafana

## DevOps

- Pipeline CI/CD
- Deploy em Cloud
- Kubernetes

## Evolução da Plataforma

- Upload de imagens
- Sistema de avaliações
- Histórico completo de pedidos
- Paginação
- Filtros avançados
- Dashboard Administrativo

---
# 📚 Documentação Técnica

Além deste README, o projeto possui um relatório técnico contendo:

- Arquitetura da aplicação
- Fluxo completo da API
- Explicação das Roles
- Funcionamento do data.sql
- Prints da API
- Casos de sucesso e erro
- Melhorias futuras
- Roadmap do projeto

📄 Arquivos disponíveis:

- docs/Relatorio_Tecnico_DeliveryTech.docx
- docs/Relatorio_Tecnico_DeliveryTech.pdf

# 👨‍💻 Desenvolvedor

## João Lucas Silva de Azevedo

Backend Developer • Java • Spring Boot

📧 **Email**

joao.azevedoluc@gmail.com

💼 **LinkedIn**

https://www.linkedin.com/in/joão-lucas-silva-de-azevedo-784711314/

🐙 **GitHub**

https://github.com/TopsideHornet0

---