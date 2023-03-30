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
~insira diagrama de árvore aqui~
**~inserir a explicação do que é cada pasta~**

## Frontend ##
O frontend foi desenvolvido utilizando o framework React JS. Ele é responsável por exibir as informações para o usuário e receber as informações inseridas por ele. O frontend está estruturado da seguinte forma:
~insira diagrama de arvore aqui~
**~inserir a explicação do que é cada pasta~**

# CPA

* [cpa_back/](.\CPA\cpa_back)
  * [src/](.\CPA\cpa_back\src)
    * [main/](.\CPA\cpa_back\src\main)
      * [java/](.\CPA\cpa_back\src\main\java)
        * [com/](.\CPA\cpa_back\src\main\java\com)
      * [resources/](.\CPA\cpa_back\src\main\resources)
        * [application.properties](.\CPA\cpa_back\src\main\resources\application.properties)
    * [test/](.\CPA\cpa_back\src\test)
      * [java/](.\CPA\cpa_back\src\test\java)
        * [com/](.\CPA\cpa_back\src\test\java\com)
  * [.gitignore](.\CPA\cpa_back\.gitignore)
  * [Dockerfile](.\CPA\cpa_back\Dockerfile)
  * [env.properties](.\CPA\cpa_back\env.properties)
  * [env.properties.example](.\CPA\cpa_back\env.properties.example)
  * [HELP.md](.\CPA\cpa_back\HELP.md)
  * [mvnw](.\CPA\cpa_back\mvnw)
  * [mvnw.cmd](.\CPA\cpa_back\mvnw.cmd)
  * [pom.xml](.\CPA\cpa_back\pom.xml)
  * [README.md](.\CPA\cpa_back\README.md)
* [cpa_front/](.\CPA\cpa_front)
  * [public/](.\CPA\cpa_front\public)
    * [index.html](.\CPA\cpa_front\public\index.html)
    * [manifest.json](.\CPA\cpa_front\public\manifest.json)
    * [robots.txt](.\CPA\cpa_front\public\robots.txt)
  * [src/](.\CPA\cpa_front\src)
    * [assets/](.\CPA\cpa_front\src\assets)
      * [css/](.\CPA\cpa_front\src\assets\css)
        * [App.css](.\CPA\cpa_front\src\assets\css\App.css)
        * [index.css](.\CPA\cpa_front\src\assets\css\index.css)
      * [image/](.\CPA\cpa_front\src\assets\image)
        * [logoCpa.jpg](.\CPA\cpa_front\src\assets\image\logoCpa.jpg)
    * [components/](.\CPA\cpa_front\src\components)
      * [Image.jsx](.\CPA\cpa_front\src\components\Image.jsx)
    * [pages/](.\CPA\cpa_front\src\pages)
      * [login.jsx](.\CPA\cpa_front\src\pages\login.jsx)
    * [routes/](.\CPA\cpa_front\src\routes)
      * [index.js](.\CPA\cpa_front\src\routes\index.js)
    * [services/](.\CPA\cpa_front\src\services)
      * [api.js](.\CPA\cpa_front\src\services\api.js)
    * [App.js](.\CPA\cpa_front\src\App.js)
    * [App.test.js](.\CPA\cpa_front\src\App.test.js)
    * [index.js](.\CPA\cpa_front\src\index.js)
    * [reportWebVitals.js](.\CPA\cpa_front\src\reportWebVitals.js)
    * [setupTests.js](.\CPA\cpa_front\src\setupTests.js)
  * [.dockerignore](.\CPA\cpa_front\.dockerignore)
  * [.gitignore](.\CPA\cpa_front\.gitignore)
  * [Dockerfile](.\CPA\cpa_front\Dockerfile)
  * [jsconfig.json](.\CPA\cpa_front\jsconfig.json)
  * [package-lock.json](.\CPA\cpa_front\package-lock.json)
  * [package.json](.\CPA\cpa_front\package.json)
  * [README.md](.\CPA\cpa_front\README.md)
  * [yarn.lock](.\CPA\cpa_front\yarn.lock)
* [mysql-init/](.\CPA\mysql-init)
  * [create_event.sql](.\CPA\mysql-init\create_event.sql)
* [.env](.\CPA\.env)
* [.env-example](.\CPA\.env-example)
* [.gitignore](.\CPA\.gitignore)
* [docker-compose.yml](.\CPA\docker-compose.yml)
* [README.md](.\CPA\README.md)
