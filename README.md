# Tic Tac Toe Multiplayer

[![Java](https://img.shields.io/badge/Java-17%2B-blue?logo=openjdk)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1%2B-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![Vue.js](https://img.shields.io/badge/Vue.js-3.3%2B-green?logo=vuedotjs)](https://vuejs.org)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-blue?logo=postgresql)](https://postgresql.org)
[![Docker](https://img.shields.io/badge/Docker-Compose-orange?logo=docker)](https://docker.com)

Многопользовательская онлайн-платформа для игры в крестики-нолики с поддержкой PvP и игры против ИИ.  
**Особенности:** JWT-авторизация, WebSocket, рейтинговая таблица, адаптивный дизайн.

![Gameplay Demo](docs/demo.gif)

## Особенности
- 🌐 **Онлайн-мультиплеер** через WebSocket
- 🤖 **Игра против ИИ** которого невозможно победить
- 🏆 **Топ игроков** с подсчетом рейтинга
- 🔒 **JWT-авторизация** с refresh-токенами
- 📱 **Адаптивный интерфейс** на Vue.js + Tailwind CSS
- 🐳 **Готовая Docker-сборка** с PostgreSQL

## Технологии
| Backend              | Frontend            | Инфраструктура       |
|----------------------|---------------------|---------------------|
| Java 17              | Vue.js 3            | Docker + Compose    |
| Spring Boot 3.1      | Tailwind CSS        | PostgreSQL 15       |
| Spring Security      | Vue Router          | JWT Auth            |
| Spring WebSocket     | Axios               | WebSocket STOMP     |

## Установка
1. Клонировать репозиторий:
```bash
git clone https://github.com/alexdarenkov/JavaTicTacToe.git
cd JavaTicTacToe
```
2. Запустить через Docker Compose:
```bash
docker-compose up --build
```
3. Перейдите по ссылке: http://localhost