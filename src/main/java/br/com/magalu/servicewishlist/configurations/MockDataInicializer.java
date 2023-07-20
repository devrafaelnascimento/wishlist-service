package br.com.magalu.servicewishlist.configurations;

import br.com.magalu.servicewishlist.entities.ClientEntity;
import br.com.magalu.servicewishlist.entities.ProductEntity;
import br.com.magalu.servicewishlist.entities.WishListEntity;
import br.com.magalu.servicewishlist.repositories.ClientRepository;
import br.com.magalu.servicewishlist.repositories.ProductRepository;
import br.com.magalu.servicewishlist.repositories.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Component
public class MockDataInicializer implements ApplicationRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishListRepository wishListRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        clientRepository.deleteAll();
        productRepository.deleteAll();
        wishListRepository.deleteAll();

        // MOCK CLIENTS

        final var clients = new ArrayList<>();

        final var client1 = new ClientEntity();
        client1.setId(UUID.fromString("fd4751d0-5296-4592-a454-71e35b888da5"));
        client1.setName("Rafael Nascimento");
        client1.setEmail("rafael.nascimento@email.com");

        final var client2 = new ClientEntity();
        client2.setId(UUID.fromString("3823bc34-ee56-4e4b-95b5-acdad949e654"));
        client2.setName("Leandro Costa");
        client2.setEmail("leandro.costa@email.com");

        clientRepository.saveAll(Arrays.asList(client1, client2));

        // MOCK PTODUCTS

        final var product1 = new ProductEntity();
        product1.setId(UUID.fromString("a0dd0969-23e7-4b97-b784-53b9ddb2d240"));
        product1.setName("Televisão");
        product1.setPrice(1499.99);

        final var product2 = new ProductEntity();
        product2.setId(UUID.fromString("a0dd0969-23e7-4b97-b784-53b9ddb2d240"));
        product2.setName("Notebook");
        product2.setPrice(2499.99);

        final var product3 = new ProductEntity();
        product3.setId(UUID.fromString("62c04877-c753-4921-ae3c-7c1b26d6a88f"));
        product3.setName("Computador");
        product3.setPrice(1999.99);

        final var product4 = new ProductEntity();
        product4.setId(UUID.fromString("8773ac0c-e025-4243-a3df-eaece6a55311"));
        product4.setName("Celular");
        product4.setPrice(999.99);

        final var product5 = new ProductEntity();
        product5.setId(UUID.fromString("c0672fda-0858-4fe8-84c4-2bdb5e1b20c0"));
        product5.setName("Tablet");
        product5.setPrice(599.99);

        final var product6 = new ProductEntity();
        product6.setId(UUID.fromString("989ee808-7def-4f11-9764-28f887e508c8"));
        product6.setName("Fogão");
        product6.setPrice(799.99);

        final var product7 = new ProductEntity();
        product7.setId(UUID.fromString("764e4df0-c147-4ab4-9081-6eb75e0c7e7b"));
        product7.setName("Mesa com 4 cadeiras");
        product7.setPrice(799.99);

        final var product8 = new ProductEntity();
        product8.setId(UUID.fromString("1a79d3f1-4a69-4aba-8cd6-942f08e5831f"));
        product8.setName("sofá retratil");
        product8.setPrice(799.99);

        final var product9 = new ProductEntity();
        product9.setId(UUID.fromString("3ceaefba-0cc8-4a5b-9922-eb7049723037"));
        product9.setName("poltrona");
        product9.setPrice(799.99);

        final var product10 = new ProductEntity();
        product10.setId(UUID.fromString("1388c6e8-2aad-4182-95af-df6f87867940"));
        product10.setName("mesa de centro");
        product10.setPrice(499.99);

        final var product11 = new ProductEntity();
        product11.setId(UUID.fromString("b159120f-bcb1-4a09-8ea5-c2538eb9cfe6"));
        product11.setName("freezer vertical");
        product11.setPrice(1899.99);

        final var product12 = new ProductEntity();
        product12.setId(UUID.fromString("54a47372-d66d-4e44-9622-a45417171ac5"));
        product12.setName("Frigobar");
        product12.setPrice(799.99);

        final var product13 = new ProductEntity();
        product13.setId(UUID.fromString("e77ab21f-8075-40f6-8977-ba28c6ad13c9"));
        product13.setName("Monitor gamer");
        product13.setPrice(1599.99);

        final var product14 = new ProductEntity();
        product14.setId(UUID.fromString("4d2c24e5-961e-42cb-a09f-84e96a2f0d44"));
        product14.setName("Cadeira gamer");
        product14.setPrice(1799.99);

        final var product15 = new ProductEntity();
        product15.setId(UUID.fromString("21e57c87-4dc8-4bfa-92f5-3c6b5857858c"));
        product15.setName("Impressora");
        product15.setPrice(499.99);

        final var product16 = new ProductEntity();
        product16.setId(UUID.fromString("665ce3fc-d4ba-4f41-a6bd-06feb065687f"));
        product16.setName("Aspirador de pó");
        product16.setPrice(599.99);

        final var product17 = new ProductEntity();
        product17.setId(UUID.fromString("47ab2c39-dcaa-4195-86cb-bacfe19e579f"));
        product17.setName("Armario de cozinha");
        product17.setPrice(999.99);

        final var product18 = new ProductEntity();
        product18.setId(UUID.fromString("98b9f5b4-091a-433f-b084-62d92bba0b3a"));
        product18.setName("Cama box");
        product18.setPrice(2799.99);

        final var product19 = new ProductEntity();
        product19.setId(UUID.fromString("0076e4fa-4fef-4c34-ad5b-b0ec4cbd6928"));
        product19.setName("forno microondas");
        product19.setPrice(799.99);

        final var product20 = new ProductEntity();
        product20.setId(UUID.fromString("e5abc6c0-4372-45f4-aa40-1ae1a6777e1d"));
        product20.setName("Maquina de lavar roupa");
        product20.setPrice(2499.99);

        final var product21 = new ProductEntity();
        product21.setId(UUID.fromString("3b3d3771-91cf-477c-a6cd-c91ebff89828"));
        product21.setName("Lavadora a jato");
        product21.setPrice(2499.99);

        productRepository.saveAll(Arrays.asList(product1, product2, product3,
                product4, product5, product6, product7, product8,
                product9, product10, product11, product12, product13,
                product14, product15, product16, product17, product18,
                product19, product20, product21));

        // MOCK WISHLIST CLIENT RAFAEL NASCIMENTO

        final var wishList1 = new WishListEntity();
        wishList1.setId(UUID.fromString("b0d7ab47-ed6f-4c18-b251-669bb3744410"));
        wishList1.setClient(client1);
        wishList1.setProduct(product1);

        final var wishList2 = new WishListEntity();
        wishList2.setId(UUID.fromString("e3aa0ff3-a18d-4e54-a663-b576a7ccde84"));
        wishList2.setClient(client1);
        wishList2.setProduct(product3);

        final var wishList3 = new WishListEntity();
        wishList3.setId(UUID.fromString("eb876101-8386-416d-8af8-b08111883037"));
        wishList3.setClient(client1);
        wishList3.setProduct(product4);

        // MOCK WISHLIST CLIENT LEANDRO COSTA

        final var wishList4 = new WishListEntity();
        wishList4.setId(UUID.fromString("c5c343b0-bdfe-49ec-8c63-9bfc00df2640"));
        wishList4.setClient(client2);
        wishList4.setProduct(product3);

        final var wishList5 = new WishListEntity();
        wishList5.setId(UUID.fromString("0c9c7f72-6553-4b83-bd1c-b43587f5023e"));
        wishList5.setClient(client2);
        wishList5.setProduct(product5);

        final var wishList6 = new WishListEntity();
        wishList6.setId(UUID.fromString("0eb2b8f9-9b76-4574-bfe6-a80c8d0fc4c1"));
        wishList6.setClient(client2);
        wishList6.setProduct(product10);

        wishListRepository.saveAll(Arrays.asList(wishList1, wishList2,
                wishList3, wishList4,
                wishList5, wishList6));
    }

}
