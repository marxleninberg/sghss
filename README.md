# SGHSS – Sistema de Gestão Hospitalar e de Serviços de Saúde

## 📌 Sobre o Projeto
O SGHSS é um sistema web desenvolvido para auxiliar na organização e gerenciamento de atendimentos em instituições de saúde. Foi projetado para oferecer recursos como agendamento de consultas, controle de usuários por perfis e registro das ações realizadas.  
O sistema foi construído com foco em segurança, autenticação robusta e em conformidade com os princípios da LGPD (Lei Geral de Proteção de Dados Pessoais).

---

## 🛠️ Tecnologias Utilizadas
- Java 17  
- Spring Boot  
- Spring Security + JWT  
- PostgreSQL  
- Maven  
- Docker  
- Postman (para testes)

---

## ⚙️ Funcionalidades Implementadas
- Cadastro público de pacientes  
- Cadastro de profissionais (restrito ao ADMIN)  
- Autenticação com geração de token JWT  
- Agendamento, reagendamento e cancelamento de consultas  
- Registro de logs de ações (login, agendamentos, etc.)  
- Exportação de logs (.txt)  
- Controle de acesso por perfil de usuário

---

## 🔒 Segurança e Controle de Acesso

- 🔐 **Autenticação:**  
  Realizada via Spring Security com uso de tokens JWT.

- 🛡️ **Autorização:**  
  Endpoints protegidos por regras específicas, garantindo acesso apenas a usuários com as permissões corretas.

- 👥 **Perfis de Acesso:**  
  - **ADMIN** – gerenciamento completo (usuários, profissionais, logs, etc.)  
  - **PROFISSIONAL** – acesso a informações de consultas e relatórios  
  - **PACIENTE** – agendamento e gerenciamento de suas próprias consultas

---

## 🧱 Estrutura e Organização
O projeto está organizado em uma estrutura em camadas, conforme abaixo:

```
sghss/
├── src/main/java/com/sghss/
│   ├── config/           → Configurações de segurança e filtros JWT
│   ├── controllers/      → Endpoints da aplicação (REST)
│   ├── services/         → Regras de negócio
│   ├── repositories/     → Interfaces de acesso ao banco de dados (JPA)
│   ├── entities/         → Entidades mapeadas
│   └── dtos/             → Objetos de transferência de dados (entrada/saída)
├── src/main/
│   └── resources/        → Arquivos de configuração (application.properties, etc.)
├── pom.xml               → Gerenciador de dependências do Maven
└── README.md             → Documentação do projeto
```

---

## ▶️ Como Executar Localmente

### Pré-requisitos:
- Java 17 instalado  
- Maven instalado  
- Docker instalado  

### Passos:
```bash
# 1. Clone o repositório
git clone https://github.com/marxleninberg/sghss.git

# 2. Entre na pasta do projeto
cd sghss

# 3. Suba o banco de dados
docker-compose up -d

# 4. Rode o sistema com sua IDE ou via terminal
mvn spring-boot:run
```

Acesse: `http://localhost:8080`

---

## 📨 Endpoints da API

### 🔐 Autenticação
- `POST /login` → Geração de token JWT
- `POST /public/cadastro` → Cadastro público de paciente

### 👤 Usuários
- `POST /usuarios` → Criar novo usuário (admin)
- `POST /usuarios/profissional` → Criar novo profissional da saúde (admin)

### 👥 Pacientes
- `GET /pacientes` → Listar todos os pacientes (admin)
- `GET /pacientes/{id}` → Buscar paciente por ID (admin)
- `DELETE /pacientes/{id}` → Remover paciente (admin)

### 📅 Consultas
- `GET /consultas` → Listar consultas do usuário (PACIENTE ou PROFISSIONAL)
- `POST /consultas` → Agendar nova consulta (PACIENTE)
- `PUT /consultas/{id}` → Reagendar consulta (PACIENTE)
- `PUT /consultas/cancelar/{id}` → Cancelar consulta (PACIENTE)
- `DELETE /consultas/{id}` → Excluir consulta (admin)
- `GET /consultas/relatorio` → Gerar relatório de atendimentos (PROFISSIONAL)

### 📋 Logs e Ações
- `GET /logs/exportar` → Exportar log de ações (admin)

---

## 🧪 Testes e Logs
- Todos os endpoints foram testados manualmente via Postman.  
- Os logs das ações são registrados no banco e podem ser exportados via:

```sql
GET /logs/exportar
Authorization: Bearer <token_do_admin>
```

---

## 📝 Observações
Este projeto foi desenvolvido como parte de um trabalho acadêmico de conclusão de curso.  
O código está organizado, comentado e preparado para evolução futura com recursos como prontuário eletrônico, relatórios e prescrições médicas.
