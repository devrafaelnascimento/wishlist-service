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

Segue abaixo as APIs disponíveis no projeto:

#### Wishlist

 - /wishlist (GET)
    - Espera um atributo para ser critério de busca no parametro da requisição, exemplo:
    ```
    clientId = fd4751d0-5296-4592-a454-71e35b888da5
    ```
 - /wishlist/add-product (POST)
    - Espera dois atributos para serem critério de busca nos parametros da requisição, exemplo:
    ```
    clientId = fd4751d0-5296-4592-a454-71e35b888da5
    productId = 8773ac0c-e025-4243-a3df-eaece6a55311
    ```
 - /wishlist/delete-product (DELETE)
 -  Espera dois atributos para serem critério de busca nos parametros da requisição, exemplo:
    ```
    clientId = fd4751d0-5296-4592-a454-71e35b888da5
    productId = 8773ac0c-e025-4243-a3df-eaece6a55311
    ```
 - /wishlist/delete-product (POST)
     - Espera um atributo para ser critérios de busca no body da requisição, exemplo:
    ```
    {
      "productName" : "Televisão"
    }
    ```

    # Collection para importar no POSTMAN
   - No diretório abaixo existe uma collection criada para consumir todos os endpoints sitados na documentação
    ```
    service-wishlist/WISHLIST_MAGALU.postman_collection.json
    ```
