# Wishlist Service Magalu

## Setup da aplicação com docker

### Pré-requisito

Antes de rodar a aplicação é preciso garantir que as seguintes dependências estejam corretamente instaladas:

```
Java 11
Docker  
Maven 3.6.0
```

### Preparando ambiente

Criar e executar container do Mongodb
```
 Acesse o terminal na pasta do projeto
 execute o arquivo docker-compose com o seguinte comando
 "docker-compose up"
```

### Instalação da aplicação

Baixar as dependência e criar imagem da aplicação

```
mvn clean package -Dmaven.test.skip=true 
```

```
Execute a aplicação pela classe ServiceWishlistApplication
Localizada em service-wishlist/src/java/br/com/magalu/servicewishlist/ServiceWishlistApplication
```

Pronto. A aplicação está disponível em http://localhost:8080

## APIs

O projeto disponibiliza uma APIs WishList, onde utiliza o padrão Rest de comunicação, produzindo e consumindo arquivos no formato JSON.

Segue abaixo o link do swagger do projeto:

### http://localhost:8080/swagger-ui.html#

    # Collection para importar no POSTMAN
   - No diretório abaixo existe uma collection criada para consumir todos os endpoints sitados na documentação
    ```
    service-wishlist/WISHLIST_MAGALU.postman_collection.json
    ```
