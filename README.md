# CouponApi

API RESTful para gerenciamento de cupons de desconto, desenvolvida com Spring Boot e arquitetura hexagonal.

## Tecnologias utilizadas:
- Java 25
- Spring Boot v.4.0.2
- Maven v.4.0.0
- H2
- JPA
- Docker
- Lombok
- Swagger

## Como rodar:
Na raíz do projeto, rodar o docker compose para realizar a criação do container e rodá-lo local:

docker-compose up --build

A aplicação e endpoints ficarão disponíveis no: localhost:8080/swagger-ui/index.html

## Decisões arquiteturais:
A arquitetura hexagonal deixa todo o escopo de regras de negócio disponível para expansão permitindo trocar a origem de dados (de JPA para JDBC, por exemplo) sem afetar regras de negócio. O mesmo para a camada de aplicação, que atualmente apenas é subida uma aplicação Web, mas pode ser criada uma versão para CLI.

