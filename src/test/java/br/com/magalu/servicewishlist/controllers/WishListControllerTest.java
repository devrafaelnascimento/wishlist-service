package br.com.magalu.servicewishlist.controllers;

import br.com.magalu.servicewishlist.entities.ClientEntity;
import br.com.magalu.servicewishlist.entities.ProductEntity;
import br.com.magalu.servicewishlist.entities.WishListEntity;
import br.com.magalu.servicewishlist.servicies.WishListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class WishListControllerTest {

    @Mock
    private WishListService service;

    @InjectMocks
    private WishListController controller;

    @Spy
    private WishListMapper mapper;

    @Test
    void getWishListOfClient() {
        final var wishList = buildWishList();
        final var client = buildClient();

        List<WishListResponse> expectedResponse = Stream.of(wishList)
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        Mockito.when(service.findAllByClientId(any(UUID.class))).thenReturn(List.of(wishList));

        ResponseEntity<List<WishListResponse>> response = controller.getWishListOfClient(client.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).findAllByClientId(client.getId());
    }

    @Test
    void addProductInWishListOfCLient() {
        final var wishList = buildWishList();
        final var client = buildClient();
        final var product = buildProduct();

        Mockito.when(service.addProductInWishListOfClient(any(UUID.class), any(UUID.class))).thenReturn(wishList);

        ResponseEntity<WishListResponse> response = controller.addProductInWishListOfCLient(client.getId(), product.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Mockito.verify(service, Mockito.times(1)).addProductInWishListOfClient(client.getId(), product.getId());

    }

    @Test
    void deleteProductInWishListOfClient() {
        final var client = buildClient();
        final var product = buildProduct();

        Mockito.doNothing().when(service).deletProductByClientInWishList(any(UUID.class), any(UUID.class));

        ResponseEntity<String> response = controller.deleteProductInWishListOfClient(client.getId(), product.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Produto removido da wishlist com sucesso", response.getBody());

        Mockito.verify(service, Mockito.times(1)).deletProductByClientInWishList(client.getId(), product.getId());

    }

    @Test
    void findProductByClient() throws JsonProcessingException {
        final var wishList = buildWishList();
        final var client = buildClient();
        String productName = "{\"productName\":\"Monitor\"}";

        WishListResponse expectedResponse = new WishListResponse();

        final var objectMapper = new ObjectMapper();
        String product = objectMapper.readTree(productName).get("productName").asText();

        Mockito.when(service.searchProductByClientInWishList(any(UUID.class), any(String.class))).thenReturn(wishList);
        Mockito.when(mapper.toResponse(wishList)).thenReturn(expectedResponse);

        ResponseEntity<WishListResponse> response = controller.getProductByClient(client.getId(), productName);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());

        Mockito.verify(service, Mockito.times(1)).searchProductByClientInWishList(client.getId(), product);
        Mockito.verify(mapper, Mockito.times(1)).toResponse(wishList);
    }

    private WishListEntity buildWishList() {
        final var wishList = new WishListEntity();
        wishList.setId(UUID.randomUUID());
        wishList.setClient(buildClient());
        wishList.setProduct(buildProduct());
        return wishList;
    }

    private ClientEntity buildClient() {
        final var client = new ClientEntity();
        client.setId(UUID.randomUUID());
        client.setName("Rafael Nascimento");
        client.setEmail("rafael.nascimento@email.com");
        return client;
    }

    private ProductEntity buildProduct() {
        final var product = new ProductEntity();
        product.setId(UUID.randomUUID());
        product.setName("Monitor");
        product.setPrice(1299.99);
        return product;
    }
}