# 🐾 Sistema de Gerenciamento de Clínica Veterinária

Sistema desktop para controle de clientes, animais e serviços de uma clínica veterinária, desenvolvido como projeto acadêmico na disciplina de Programação Orientada a Objetos.

<p align="center">
  <img src="https://skillicons.dev/icons?i=java,maven,idea,mysql,bash,github,git,css,docker" alt="Tecnologias utilizadas"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-11.0.24_LTS-orange" alt="Java 11"/>
  <img src="https://img.shields.io/badge/Maven-4.0.0-blue" alt="Maven"/>
  <img src="https://img.shields.io/badge/MySQL-9.0.0-4479A1" alt="MySQL"/>
  <img src="https://img.shields.io/badge/JavaFX-UI-brightgreen" alt="JavaFX"/>
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED" alt="Docker"/>
</p>

---

## Sobre o Projeto

Este repositório apresenta uma solução completa para o gerenciamento de uma clínica veterinária, permitindo o cadastro e controle de **clientes** e **animais** por meio de uma interface gráfica construída em JavaFX, com persistência de dados em um banco MySQL.

O projeto foi desenvolvido com foco em boas práticas de Programação Orientada a Objetos, aplicando padrões de projeto como **DAO** e **DTO** para garantir separação de responsabilidades, manutenibilidade e reutilização de código.

## Tecnologias Utilizadas

| Tecnologia | Descrição |
|---|---|
| **Java 11.0.24 LTS** | Linguagem principal do sistema |
| **JavaFX** | Construção da interface gráfica |
| **Maven 4.0.0** | Gerenciamento de dependências e build |
| **MySQL 9.0.0** | Banco de dados relacional |
| **Docker / Docker Compose** | Orquestração e inicialização do banco de dados |

## Dependências Principais

- **MySQL Connector/J** — integração da aplicação com o banco de dados MySQL.
- **Kordamp Ikonli** — gerenciamento de ícones no sistema:
  - `ikonli-javafx`
  - `ikonli-fontawesome5`
  - `ikonli-devicons`
  - `ikonli-fontawesome`
  - `ikonli-materialdesign2`
- **JavaFX-FXML** — criação e manipulação de layouts em FXML.
- **JavaFX-Media** — suporte a multimídia na aplicação.
- **Apache XML Graphics** — manipulação de gráficos em XML.

## Funcionalidades

O sistema permite realizar todas as operações de **CRUD** (Criar, Ler, Atualizar e Deletar) para as seguintes entidades:

- 👤 **Cliente**
- 🐶 **Animal**

## Arquitetura do Sistema

- **Orientação a Objetos** — foco em modularidade, coesão e reutilização de código.
- **DAO (Data Access Object)** — padrão utilizado para abstrair e organizar o acesso ao banco de dados.
- **DTO (Data Transfer Object)** — utilizado para transferir dados de forma segura e desacoplada entre as camadas do sistema.

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── sistem/
│   │       ├── model/
│   │       │   ├── db/          # Configuração e conexão com o banco de dados
│   │       │   ├── entities/    # Entidades do sistema
│   │       │   │   └── dto/     # Objetos de transferência de dados (DTO)
│   │       │   ├── enums/       # Enumerações utilizadas no sistema
│   │       │   └── exceptions/  # Exceções personalizadas
│   │       ├── interfaces/
│   │       │   └── dao/         # Interfaces de acesso aos dados
│   │       │       └── impl/    # Implementações das interfaces DAO
│   │       ├── service/         # Regras de negócio e manipulação de dados (ex.: máscaras)
│   │       ├── app/              # Classes principais da aplicação
│   │       ├── controller/       # Controladores de interface e lógica de aplicação
│   │       └── module-info.java  # Arquivo de módulo do Java
│   │
│   └── resources/
│       ├── fxml/     # Arquivos de layout FXML
│       ├── imgs/     # Imagens e ícones da interface
│       └── styles/   # Arquivos de estilo (CSS)
```

## Como Executar o Projeto

### Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Java JDK 11+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker e Docker Compose](https://docs.docker.com/get-docker/)
- Git

### Passo a passo

1. **Clone este repositório:**
   ```bash
   git clone https://github.com/GustavoOlSantos/sistema-clinica-veterinaria.git
   ```

2. **Acesse a pasta do projeto e abra em sua IDE de preferência** (ex.: IntelliJ IDEA, Eclipse, VS Code).

3. **Inicialize o banco de dados MySQL com Docker:**
   ```bash
   docker compose up -d
   ```

4. **Compile e execute a aplicação:**
   ```bash
   ./runme.bat
   ```
   Esse script executa os lifecycles do Maven (`clean` e `install`, pulando os testes para agilizar o processo) e, em seguida, inicializa a aplicação a partir do `.jar` gerado.

> 💡 **Nota:** o script `runme.bat` é voltado para ambientes Windows. Em Linux/macOS, execute manualmente:
> ```bash
> mvn clean install -DskipTests
> java -jar target/nome-do-jar.jar
> ```

## Licença

Este projeto foi desenvolvido para fins acadêmicos, como parte da disciplina de Programação Orientada a Objetos.

---

<p align="center">Desenvolvido com 🐾 por <a href="https://github.com/GustavoOlSantos">Gustavo Oliveira Santos</a></p>