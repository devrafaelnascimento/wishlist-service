package br.com.magalu.servicewishlist.servicies.impl;

import br.com.magalu.servicewishlist.entities.ClientEntity;
import br.com.magalu.servicewishlist.entities.ProductEntity;
import br.com.magalu.servicewishlist.entities.WishListEntity;
import br.com.magalu.servicewishlist.repositories.ClientRepository;
import br.com.magalu.servicewishlist.repositories.ProductRepository;
import br.com.magalu.servicewishlist.repositories.WishListRepository;
import br.com.magalu.servicewishlist.servicies.exceptions.ClientNotFoundException;
import br.com.magalu.servicewishlist.servicies.exceptions.ProductNotFoundException;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

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

        final var result = service.findAllByClientId(client.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        Mockito.verify(repository, times(1)).findAllByClientId(client.getId());
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

        final var result = service.addProductInWishListOfClient(client.getId(), product.getId());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(client.getId(), result.getClient().getId());
        Assertions.assertEquals(product.getId(), result.getProduct().getId());

        Mockito.verify(clientRepository, times(1)).findById(client.getId());
        Mockito.verify(productRepository, times(1)).findById(product.getId());
        Mockito.verify(repository, times(1)).save(any(WishListEntity.class));
    }

    @Test
    void deletProductByClientInWishList() {
        final var client = buildClient();
        final var product = buildProduct();
        final var wishlistItem = buildWishList();

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        Mockito.when(repository.findByClientIdAndProductId(any(UUID.class), any(UUID.class))).thenReturn(Optional.of(wishlistItem));

        service.deletProductByClientInWishList(client.getId(), product.getId());

        Mockito.verify(clientRepository, times(1)).findById(client.getId());
        Mockito.verify(productRepository, times(1)).findById(product.getId());
        Mockito.verify(repository, times(1)).findByClientIdAndProductId(client.getId(), product.getId());
        Mockito.verify(repository, times(1)).delete(wishlistItem);
    }

    @Test
    void testDeletProductByClientInWishList_ProductNotFound() {
        final var client = buildClient();
        final var product = buildProduct();

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            service.deletProductByClientInWishList(client.getId(), product.getId());
        });

        Mockito.verify(clientRepository, times(1)).findById(client.getId());
        Mockito.verify(productRepository, times(1)).findById(product.getId());
        Mockito.verify(repository, never()).findByClientIdAndProductId(any(), any());
        Mockito.verify(repository, never()).delete(any());
    }

    @Test
    public void testDeleteProductByClientInWishList_WishlistItemNotFound() {
        final var client = buildClient();
        final var product = buildProduct();

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        Mockito.when(repository.findByClientIdAndProductId(any(UUID.class), any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            service.deletProductByClientInWishList(client.getId(), product.getId());
        });

        Mockito.verify(clientRepository, times(1)).findById(client.getId());
        Mockito.verify(productRepository, times(1)).findById(product.getId());
        Mockito.verify(repository, times(1)).findByClientIdAndProductId(client.getId(), product.getId());
        Mockito.verify(repository, never()).delete(any());
    }

    @Test
    void searchProductByClientInWishList() {
        final var wishlistItem = buildWishList();
        final var client = buildClient();
        final var productName = "Monitor";

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(repository.searchProductByClientInWishList(any(UUID.class), any(String.class))).thenReturn(Optional.of(wishlistItem));

        final var result = service.searchProductByClientInWishList(client.getId(), productName);

        Mockito.verify(clientRepository, times(1)).findById(client.getId());
        Mockito.verify(repository, times(1)).searchProductByClientInWishList(client.getId(), productName);

        assertEquals(wishlistItem, result);
    }

    @Test
    public void testSearchProductByClientInWishList_ProductNotFound() {
        final var client = buildClient();
        final var productName = "Monitor";

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        Mockito.when(repository.searchProductByClientInWishList(any(UUID.class), any(String.class))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            service.searchProductByClientInWishList(client.getId(), productName);
        });

        Mockito.verify(clientRepository, times(1)).findById(client.getId());
        Mockito.verify(repository, times(1)).searchProductByClientInWishList(client.getId(), productName);
    }

    @Test
    public void testGetClientById_Success() {
        final var client = buildClient();

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));

        final var result = service.getClientById(client.getId());

        Mockito.verify(clientRepository, times(1)).findById(client.getId());

        assertEquals(client, result);
    }

    @Test
    public void testGetClientById_ClientNotFound() {
        final var client = buildClient();

        Mockito.when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            service.getClientById(client.getId());
        });

        Mockito.verify(clientRepository, times(1)).findById(client.getId());
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