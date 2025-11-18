# Sistema de Gerenciamento de Clínica Veterinária

### Bem-vindo!
Este repositório apresenta uma solução para controle de clientes, animais e serviços em uma clínica veterinária, desenvolvido na faculdade, ao cursar a disciplina Programação Orientada a Objetos. 

## Tecnologias Utilizadas 

- **JavaFX**: Interface gráfica.
- **Java**: Versão 11.0.24 LTS.
- **Maven**: Gerenciamento de dependências e build (versão 4.0.0).
- **Banco de Dados MySQL**: Versão 9.0.0.

<img src="https://skillicons.dev/icons?i=java,maven,idea,mysql,bash,github,git,css"/>

### Dependências Principais

- **MySQL Connector J**: Integração com o banco de dados MySQL.
- **Kordamp Ikonli**: Gerenciamento de ícones no sistema.
  - Ikonli JavaFX
  - ikonli-fontawesome5
  - ikonli-devicons
  - ikonli-fontawesome
  - ikonli-materialdesign2
- **JavaFX-FXML**: Criação e manipulação de layouts em FXML.
- **JavaFX-Media**: Suporte à multimídia.
- **Apache XML Graphics**: Manipulação de gráficos em XML.

## Funcionalidades

Este sistema é capaz de realizar todas as operações CRUD (Criar, Ler, Atualizar e Deletar) para as seguintes entidades:

1. **Cliente**
2. **Animal**

### Arquitetura do Sistema

- **Orientado a Objetos**: Foco em modularidade e reutilização de código.
- **DAO (Data Access Object)**: Padrão utilizado para interação com o banco de dados.
- **DTO (Data Transfer Object)**: Transferência de dados entre diferentes camadas do sistema.

## Como Executar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/GustavoOlSantos/sistema-clinica-veterinaria.git
   ```

2. Importe o projeto em sua IDE de preferência.

3. Configure o banco de dados MySQL:
   - Crie uma base de dados com o script em `scripts/database.sql`.
   - Crie um arquivo db.properties com os campos: `user, password, dburl, useSSL=true`.

4. Execute as goals do maven `clean` e `install` no arquivo `pom.xml`.
5. Execute o projeto pela IDE (no arquivo main.java).

## Estrutura do Projeto

- **src/main/java**: Contém os arquivos fonte do sistema.
  - **sistem.model.db**: Classes de configuração e conexão ao banco de dados.
  - **sistem.model.entities**: Classes que representam as entidades do sistema.
  - **sistem.model.entities.dto**: Objetos de transferência de dados (DTO).
  - **sistem.model.enums**: Enumerações utilizadas no sistema.
  - **sistem.model.exceptions**: Tratamento de exceções personalizadas.
  - **sistem.interfaces.dao**: Interfaces para acesso aos dados.
  - **sistem.interfaces.dao.impl**: Implementações das interfaces DAO.
  - **sistem.service**: Manipulação dos dados, como máscaras.
  - **sistem.app**: Classes principais do sistema.
  - **sistem.controller**: Controladores de interface e lógica da aplicação.
  - **module-info.java**: Arquivo de módulo do Java.

- **src/main/resources**: Contém recursos adicionais do sistema.
  - **fxml**: Arquivos de layout FXML.
  - **imgs**: Imagens e ícones utilizados na interface.
  - **styles**: Arquivos de estilo (CSS).
