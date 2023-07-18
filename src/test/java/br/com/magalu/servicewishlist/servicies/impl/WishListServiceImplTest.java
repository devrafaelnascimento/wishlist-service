package br.com.magalu.servicewishlist.servicies.impl;

import br.com.magalu.servicewishlist.entities.ClientEntity;
import br.com.magalu.servicewishlist.entities.ProductEntity;
import br.com.magalu.servicewishlist.entities.WishListEntity;
import br.com.magalu.servicewishlist.repositories.ClientRepository;
import br.com.magalu.servicewishlist.repositories.ProductRepository;
import br.com.magalu.servicewishlist.repositories.WishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class WishListServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WishListRepository repository;

    @InjectMocks
    private WishListServiceImpl service;

    @Test
    void findAllByClientId() {
        final var wishList = buildWishList();
        final var client = buildClient();

        Mockito.when(repository.findAllByClientId(any(UUID.class))).thenReturn(List.of(wishList));

        List<WishListEntity> result = service.findAllByClientId(client.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        Mockito.verify(repository, Mockito.times(1)).findAllByClientId(client.getId());
    }

    @Test
    void addProductInWishListOfClient() {
        final var client = buildClient();
        final var product = buildProduct();

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));

        Mockito.when(repository.save(any(WishListEntity.class)))
                .thenAnswer(invocation -> {
                    WishListEntity wishlistItem = invocation.getArgument(0);
                    wishlistItem.setId(UUID.randomUUID());
                    return wishlistItem;
                });

        WishListEntity result = service.addProductInWishListOfClient(client.getId(), product.getId());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(client.getId(), result.getClient().getId());
        Assertions.assertEquals(product.getId(), result.getProduct().getId());

        Mockito.verify(clientRepository, Mockito.times(1)).findById(client.getId());
        Mockito.verify(productRepository, Mockito.times(1)).findById(product.getId());
        Mockito.verify(repository, Mockito.times(1)).save(any(WishListEntity.class));
    }

    @Test
    void deletProductByClientInWishList() {
        final var client = buildClient();
        final var product = buildProduct();

        WishListEntity wishlistItem = new WishListEntity();
        wishlistItem.setClient(client);
        wishlistItem.setProduct(product);

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(repository.findByClientIdAndProductId(client.getId(), product.getId())).thenReturn(Optional.of(wishlistItem));

        service.deletProductByClientInWishList(client.getId(), product.getId());

        Mockito.verify(clientRepository, Mockito.times(1)).findById(client.getId());
        Mockito.verify(repository, Mockito.times(1)).findByClientIdAndProductId(client.getId(), product.getId());
        Mockito.verify(repository, Mockito.times(1)).delete(wishlistItem);
    }

    @Test
    void testDeletProductByClientInWishList_ClientNotFound() {
        final var client = buildClient();
        final var product = buildProduct();

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.deletProductByClientInWishList(client.getId(), product.getId());
        });

        Mockito.verify(clientRepository, Mockito.times(1)).findById(client.getId());
        Mockito.verify(repository, Mockito.never()).findByClientIdAndProductId(Mockito.any(), Mockito.any());
        Mockito.verify(repository, Mockito.never()).delete(Mockito.any(WishListEntity.class));
    }

    @Test
    void testDeletProductByClientInWishList_ProductNotFound() {
        final var client = buildClient();
        final var product = buildProduct();

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(repository.findByClientIdAndProductId(any(UUID.class), any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.deletProductByClientInWishList(client.getId(), product.getId());
        });

        Mockito.verify(clientRepository, Mockito.times(1)).findById(client.getId());
        Mockito.verify(repository, Mockito.times(1)).findByClientIdAndProductId(client.getId(), product.getId());
        Mockito.verify(repository, Mockito.never()).delete(Mockito.any(WishListEntity.class));
    }

    @Test
    void searchProductByClientInWishList() {
        final var wishlistItem = buildWishList();
        final var client = buildClient();
        final var productName = "Monitor";



        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(repository.searchProductByClientInWishList(any(UUID.class), any(String.class))).thenReturn(Optional.of(wishlistItem));

        WishListEntity result = service.searchProductByClientInWishList(client.getId(), productName);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(wishlistItem.getId(), result.getId());

        Mockito.verify(clientRepository, Mockito.times(1)).findById(client.getId());
        Mockito.verify(repository, Mockito.times(1)).searchProductByClientInWishList(client.getId(), productName);
    }

    @Test
    void testSearchProductByClientInWishList_ClientNotFound() {
        final var client = buildClient();
        final var productName = "Monitor";

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.searchProductByClientInWishList(client.getId(), productName);
        });

        Mockito.verify(clientRepository, Mockito.times(1)).findById(client.getId());
        Mockito.verify(repository, Mockito.never()).searchProductByClientInWishList(Mockito.any(), Mockito.any());
    }

    @Test
    void testSearchProductByClientInWishList_ProductNotFound() {
        final var client = buildClient();
        final var productName = "Monitor";

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(repository.searchProductByClientInWishList(any(UUID.class), any(String.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.searchProductByClientInWishList(client.getId(), productName);
        });

        Mockito.verify(clientRepository, Mockito.times(1)).findById(client.getId());
        Mockito.verify(repository, Mockito.times(1)).searchProductByClientInWishList(client.getId(), productName);
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