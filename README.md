# 🛒 E-commerce Microservices (Event-Driven Architecture)

Este projeto é uma simulação de um sistema de e-commerce moderno utilizando **Arquitetura de Microsserviços** e **Event-Driven Architecture**.

## 🚀 Visão Geral

O sistema é composto por múltiplos serviços independentes que se comunicam de forma assíncrona via eventos utilizando Kafka.

## 🧩 Arquitetura

- Arquitetura Hexagonal (Ports & Adapters)
- Microsserviços independentes
- Comunicação assíncrona com Kafka

## ⚙️ Tecnologias

- Java + Spring Boot
- Apache Kafka
- PostgreSQL
- Docker + Docker Compose

## 📦 Serviços

### 🧾 Order Service
- Cria pedidos
- Persiste no banco
- Publica evento `order.created`

### 💳 Payment Service
- Consome `order.created`
- Processa pagamento
- Publica `payment.processed` ou `payment.failed`

### 📦 Inventory Service
- Reserva estoque
- Reage a eventos de pedido e pagamento

### 🔔 Notification Service
- Consome eventos
- Envia notificações (log/email)

## 🔄 Fluxo de Eventos


Cliente → Order Service → Kafka (order.created)
→ Payment Service → Kafka (payment.processed)
→ Inventory Service
→ Notification Service


## 🐳 Como rodar o projeto

```bash
docker-compose up --build
📡 Exemplo de requisição

POST /orders

{
  "customerId": "11111111-1111-1111-1111-111111111111",
  "items": [
    {
      "productId": "22222222-2222-2222-2222-222222222222",
      "productName": "Notebook Gamer",
      "quantity": 2,
      "unitPrice": 2500.00
    }
  ]
}
```bash
git add README.md
git commit -m "docs: add professional README"
git push
