# BibliotecAlura

  <img src="https://github.com/DanniNascimento/BibliotecAlura/blob/main/badge-literalura.png" alt="Pesquisar" min-width="400px" max-width="150px" width="250px" align="center">

**BibliotecAlura** é um sistema de gerenciamento de biblioteca desenvolvido em Java, utilizando Spring Boot para a criação de APIs RESTful, PostgreSQL para o armazenamento de dados e integração com a API pública **Gutendex** para busca de livros. O sistema oferece funcionalidades como cadastro de livros e autores, filtragem por idioma e a possibilidade de buscar livros através de títulos utilizando o Gutendex.

## Funcionalidades

- **Cadastro de livros e autores**: Permite registrar livros e autores na base de dados.
- **Busca por livros via Gutendex**: Integração com a API Gutendex para buscar livros por título.
- **Exibição de informações detalhadas**: O sistema exibe dados sobre livros, incluindo título, autores, idiomas e número de downloads.
- **Filtragem de livros por idioma**: Permite filtrar livros cadastrados pelo idioma.

## Tecnologias

- **Java 17**: Linguagem de programação.
- **Spring Boot**: Framework para construção de APIs RESTful.
- **JPA/Hibernate**: Para mapeamento objeto-relacional.
- **PostgreSQL**: Banco de dados relacional.
- **Gutendex API**: Integração com a API pública do Gutendex para busca de livros.
- **Jackson**: Biblioteca para manipulação de JSON.

<div>
  <img align="center" alt="Danni-C#" height="30" width="85" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img align="center" alt="Danni-C#" height="30" width="85" src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white">
  <img align="center" alt="Danni-C#" height="30" width="85" src="https://img.shields.io/badge/Google_Cloud-4285F4?style=for-the-badge&logo=google-cloud&logoColor=white">
  <img align="center" alt="Danni-C#" height="30" width="85" src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white">
  <img align="center" alt="Danni-C#" height="30" width="85" src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens">
  <img align="center" alt="Danni-C#" height="30" width="85" src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white">
  <img align="center" alt="Danni-C#" height="30" width="85" src="https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white">
</div>

## Instalação

### Requisitos

- Java 17 ou superior.
- Maven ou Gradle.
- PostgreSQL instalado e configurado.

### Passos para configurar o projeto

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/seu_usuario/BibliotecAlura.git
   cd BibliotecAlura
   ```

   ### Configuração do banco de dados PostgreSQL

2. **Crie um banco de dados no PostgreSQL (exemplo: `bibliotecalura`) e configure as credenciais no arquivo `src/main/resources/application.properties**:

 ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/bibliotecalura
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

3. **Compile e execute o projeto**:
   
   **Se estiver utilizando Maven**:

```bash
   mvn clean install
   mvn spring-boot:run
```
   **Ou, se estiver utilizando Gradle**:

```bash
  gradle build
  gradle bootRun
```
**O aplicativo será iniciado na porta 8080 por padrão**.

### Interface de Linha de Comando (CLI)

O sistema possui uma interface interativa via linha de comando (CLI), onde o usuário pode realizar as seguintes ações:

Adicionar livros e autores.

Buscar livros via título utilizando a Gutendex API.

Filtrar livros por idioma.

Ao executar o programa, o menu será exibido e o usuário poderá escolher as operações desejadas.

Exemplos de operação:

1. **Adicionar um livro**:

Você pode adicionar um livro e associar autores à base de dados diretamente via CLI ou diretamente no banco de dados.

<img src="https://github.com/DanniNascimento/BibliotecAlura/blob/main/hora%20da%20pesquisa.png" alt="Pesquisar" min-width="400px" max-width="350px" width="450px" align="center">

2. **Buscar livro via Gutendex**:

Digite o título do livro que deseja buscar e o sistema realizará uma busca na Gutendex API e, se o livro for encontrado, ele será registrado automaticamente.

<img src="https://github.com/DanniNascimento/BibliotecAlura/blob/main/buscarlivro.png" alt="Pesquisar" min-width="400px" max-width="350px" width="450px" align="center">

3. **Filtrar livros por idioma**:

O sistema permite filtrar livros por idioma e exibir somente os livros que correspondem ao idioma escolhido.

<img src="https://github.com/DanniNascimento/BibliotecAlura/blob/main/linguas.png" alt="Pesquisar" min-width="400px" max-width="350px" width="450px" align="center">

### Estrutura do Projeto

**Aqui está a estrutura de diretórios e arquivos principais do projeto**:

```bash
BibliotecAlura/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/
│   │   │       └── com/
│   │   │           └── alura/
│   │   │               └── BibliotecAlura/
│   │   │                   ├── model/          # Modelos de dados (Livro, Autor, BuscaPorLivros)
│   │   │                   ├── repository/      # Repositórios JPA (LivroRepository, AutorRepository)
│   │   │                   ├── service/         # Serviços de lógica de negócios (LivroService, AutorService, GutendexService)
│   │   │                   ├── Menu/            # Menu CLI
│   │   │                   ├── BibliotecAluraApplication.java  # Classe principal para rodar o Spring Boot
│   │   │                   ├── Run.java          # Classe para iniciar o menu CLI
│   │   └── resources/
│   │       ├── application.properties  # Configurações do Spring Boot
│   │       └── static/                 # Arquivos estáticos (se aplicável)
│   └── test/                           # Testes unitários e de integração

```

### Contribuição

Se você deseja contribuir com o projeto, sinta-se à vontade para realizar modificações ou melhorar funcionalidades.
Se encontrar um bug ou tiver sugestões de melhorias, crie um issue ou faça um pull request.

### Passos para contribuir:

1. **Faça um fork do repositório**.

2. **Crie uma branch para a sua feature**
```bash
git checkout -b feature/nova-feature
```
3. **Comite suas mudanças**
```bash
git commit -m 'Adiciona nova feature
```
4. **Envie para a branch**
```bash
git push origin feature/nova-feature
```
5. **Crie um pull request para revisão**.


### STATUS
<img src="http://img.shields.io/static/v1?label=STATUS&message=CONCLUIDO&color=GREEN&style=for-the-badge"/>

### Tutores do Projeto

O desafio criado para aprimorar o conhecimento adquirido durante o curso de Java e SpringBoot Dev da Alura com o apoio dos seguintes tutores:

<div style="display: flex; gap: 20px;">
  <div>
    <a href="https://github.com/iasminaraujoc" target="_blank">
      <img loading="lazy" src="https://avatars.githubusercontent.com/u/84939115?v=4" width="115">
    </a>
    <br>
    <sub>Iasmin Araújo - Alura Scuba Team</sub>
  </div>

  <div>
    <a href="https://github.com/jacqueline-oliveira" target="_blank">
      <img loading="lazy" src="https://avatars.githubusercontent.com/u/66698429?v=4" width="115">
    </a>
    <br>
    <sub>Jacqueline Oliveira - Tech Educator e Professor na Alura e na USP</sub>
  </div>
</div>

  ### Autores

[<img loading="lazy" src="https://avatars.githubusercontent.com/u/124941926?v=4" width=115><br><sub>Danni Nascimento Da Rocha</sub>](https://github.com/DanniNascimento)

##
