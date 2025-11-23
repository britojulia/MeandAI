# Me And AI 🤖✨

**Me and AI** é uma plataforma criada para mostrar que o futuro do trabalho não é sobre substituir pessoas por máquinas, mas sim sobre pessoas e tecnologia trabalhando juntas.
Ele usa inteligência artificial para ajudar profissionais a entenderem quais habilidades precisam desenvolver, como evoluir na carreira e criar trilhas personalizadas para os seus objetivos.

## 🎯 Propósito do Projeto
O MeandAI nasceu com a missão de **empoderar pessoas através da tecnologia**.

A ideia é simples:
- ✔ A IA como parceira
- ✔ O ser humano no centro
- ✔ Crescimento profissional contínuo
- ✔ Educação acessível
- ✔ Preparação real para o futuro do trabalho

No MeandAI, o usuário cria um perfil, e a plataforma usa IA para analisar:

- Suas habilidades
- Suas experiências
- Seu objetivo de carreira

A partir disso, a IA gera automaticamente uma **trilha personalizada de aprendizado**, com conteúdos e etapas recomendadas especificamente para aquele usuário.

---

## 📌Funcionalidades Principais do MeandAI

- **Autenticação via OAuth2**:Login com Google, Sessão segura com Spring Security e Redirecionamento automático após login.
- **Geração de Trilhas com IA**:Formulário de habilidades (FormSkill), Análise do perfil eRoteiro completo recomendado pela IA.
- **Histórico de trilhas geradas**:Registro de todas as trilhas criadas, com opção de visualizar detalhes, revisar etapas e gerenciar trilhas salvas  
- **IA conversasional**:Assistente de IA integrado, com foco em carreira, aprendizado e orientação profissional.  
- **Interface Moderna**: Thymeleaf + Tailwind + DaisyUI, Layout limpo e responsivo.  
- **Internacionalização**: Suporte para Português e Inglês.  
---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security (OAuth2 Login com Google)**
- **Spring AI (Google GenAI)**
- **Spring Cache (Caching)**
- **Spring AMQP (RabbitMQ)**
- **Thymeleaf**
- **Internationalization (i18n)**
- **Lombok**
- **Tailwind CSS + DaisyUI**
- **PostgreSQL (via Docker)**
---

## 🚀 Execução do Projeto (IntelliJ + Docker)
### Deploy
### 1. A aplicação está disponível em:
```bash
https://meandai.onrender.com/login
```
> Não é necessário configurar variáveis de ambiente localmente, elas já devem estar definidas.


### Localmente
### 1. Clone o repositório
```bash
git clone https://github.com/britojulia/MeandAI.git
cd MeandAI
```

### 2. Abra o Docker Desktop

### 3. Crie a conexão com o banco de dados

- No canto superior direito → clique no botão Database → clique no + → Datasource.
- Selecione PostgreSQL.
- Insira usuário, senha e nome do database conforme definido no compose.yaml.
- Clique em Test Connection e depois em Apply.
- Rode o banco de dados

### 4. Crie as variáveis de ambiente
crie e configure as variavies de ambiente >
```bash
GEMINI_API_KEY
PROJECT_ID
GOOGLE_ID 
GOOGLE_SECRET
````

### 5. Rode e acesse a aplicação
 1- Abra o projeto no IntelliJ.

 2- Run → MeAndAIApplication.

 3- Acesse no navegador: http://localhost:8080/login

## 🎥 Pitch e Demonstração da Solução

* 🔗 Pitch:
* 🎮 Vídeo da solução completa: https://youtu.be/rDH0h4ZdFy4?si=bXfeNTkJ_Qj6g9KC

##  Desenvolvido por:
Julia Brito, Leandro Correia, Victor Antonopoulos - 2TDSPG
