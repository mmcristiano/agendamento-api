# Desafio Técnico backend Conexa

Descrição:

API REST para médicos de plantão agendarem atendimentos com pacientes

Pré requisitos:
- Git
- Java 8
- Maven
- Docker



# Banco de dados

Configurado via Docker.

Na pasta raiz do projeto encontra-se o arquivo docker-compose.yml, para subir o banco de dados, executar o comando abaixo, via terminal: 

```
$ docker-compose up
```

# Aplicação

Para executar a aplicação basta executar o comando abaixo, via terminal: 

```
 $ mvn spring-boot:run
```



#Swagger
Os endpoints estão disponíveis através do swagger no link abaixo: 
```
 $ http://localhost:8060/
```
Para acessar uma rota protegida deve-se realizar o login e adicionar o token retornado no swagger no formato:  

```
Bearer {{token}}
```


#Testes

Para executar os testes basta executar o comando abaixo, via terminal: 

```
 $ mvn test
```




Médicos cadastrados:

```
usuario: antonio.chacra
senha: antonio.chacra 

usuario: eduardo.mutarelli
senha: eduardo.mutarelli 

usuario: gilberto.camanho
senha: gilberto.camanho 
```

#Docker

É possível subir a aplicação juntamente com o banco de dados, via docker.

Passo a passo: 


1) Descomentar as linhas no arquivo docker-compose.yml. 
2) alterar o valor da propriedade 'spring.datasource.url' no arquivo 'application.properties' da aplicação.

De:


```
jdbc:mysql://localhost:3306/agenda?createDatabaseIfNotExist=true
```
Para:
```
jdbc:mysql://mysqldb:3306/agenda?createDatabaseIfNotExist=true
```
3) Na raiz da aplicação, executar via terminal o comando abaixo: 
```
$  mvn package -DskipTests
```
4) Por fim, ainda com o terminal na raiz da aplicação, executar o comando:
```
$ docker-compose up
```