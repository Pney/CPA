# **Plataforma CPA**
## *Plataforma web desenvolvida para atender a Comissão Própria de Avaliação do BioPark.* O projeto utiliza Spring Boot como framework do backend e React JS para o frontend.

### Configuração do ambiente de desenvolvimento ###
Para configurar o ambiente de desenvolvimento, siga os seguintes passos:

### Pré-requisitos: ###
* Java 17 instalado
* Node.js instalado
* Yarn ou npm instalado

### Instalação: ###
* Clone o repositório
* Abra o projeto em seu IDE
* Acesse a pasta cpa_back com `cd cpa_back` e instale as dependências do backend executando `mvn clean package`
* Acesse a pasta do frontend com cd `../cpa_front` e instale as dependências com `yarn install`

### Executando o projeto: ###
* No terminal, acesse novamente a pasta cpa_back
* Execute o comando `mvn spring-boot:run` para iniciar o backend
* Abra outro terminal e acesse a pasta do frontend com `cd cpa_front`
* Execute o comando `yarn start` para iniciar o frontend
* Acesse o endereço http://localhost:3000 em seu navegador para utilizar a aplicação


## Estrutura do projeto ##
O projeto está dividido em duas partes principais: backend e frontend.

## Backend ##
O backend foi desenvolvido utilizando o framework Spring Boot. Ele é responsável por gerenciar as requisições do frontend, processar as informações recebidas e enviar as respostas adequadas. O backend está estruturado da seguinte forma:

# cpa_back
* [src/](.\cpa_back\src)
  * [main/](.\cpa_back\src\main)
    * [java/](.\cpa_back\src\main\java)
      * [com/](.\cpa_back\src\main\java\com)
        * [biopark/](.\cpa_back\src\main\java\com\biopark)
          * [cpa/](.\cpa_back\src\main\java\com\biopark\cpa)
            * [config/](.\cpa_back\src\main\java\com\biopark\cpa\config)
            * [controllers/](.\cpa_back\src\main\java\com\biopark\cpa\controllers)
            * [entities/](.\cpa_back\src\main\java\com\biopark\cpa\entities)
            * [repository/](.\cpa_back\src\main\java\com\biopark\cpa\repository)
            * [services/](.\cpa_back\src\main\java\com\biopark\cpa\services)
            * [CpaApplication.java](.\cpa_back\src\main\java\com\biopark\cpa\CpaApplication.java)
    * [resources/](.\cpa_back\src\main\resources)
      * [application.properties](.\cpa_back\src\main\resources\application.properties)
  * [test/](.\cpa_back\src\test)
    * [java/](.\cpa_back\src\test\java)
      * [com/](.\cpa_back\src\test\java\com)
        * [biopark/](.\cpa_back\src\test\java\com\biopark)
          * [cpa/](.\cpa_back\src\test\java\com\biopark\cpa)
            * [CpaApplicationTests.java](.\cpa_back\src\test\java\com\biopark\cpa\CpaApplicationTests.java)
* [.gitignore](.\cpa_back\.gitignore)
* [Dockerfile](.\cpa_back\Dockerfile)
* [env.properties](.\cpa_back\env.properties)
* [env.properties.example](.\cpa_back\env.properties.example)
* [HELP.md](.\cpa_back\HELP.md)
* [pom.xml](.\cpa_back\pom.xml)
* [README.md](.\cpa_back\README.md)


**~inserir a explicação do que é cada pasta~**

## Frontend ##
O frontend foi desenvolvido utilizando o framework React JS. Ele é responsável por exibir as informações para o usuário e receber as informações inseridas por ele. O frontend está estruturado da seguinte forma:
~insira diagrama de arvore aqui~
**~inserir a explicação do que é cada pasta~**