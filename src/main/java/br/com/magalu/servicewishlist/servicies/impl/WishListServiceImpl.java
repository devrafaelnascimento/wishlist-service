package br.com.magalu.servicewishlist.servicies.impl;

import br.com.magalu.servicewishlist.entities.ClientEntity;
import br.com.magalu.servicewishlist.entities.ProductEntity;
import br.com.magalu.servicewishlist.servicies.WishListService;
import br.com.magalu.servicewishlist.entities.WishListEntity;
import br.com.magalu.servicewishlist.repositories.ClientRepository;
import br.com.magalu.servicewishlist.repositories.ProductRepository;
import br.com.magalu.servicewishlist.repositories.WishListRepository;
import br.com.magalu.servicewishlist.servicies.exceptions.ClientNotFoundException;
import br.com.magalu.servicewishlist.servicies.exceptions.ProductNotFoundException;
import br.com.magalu.servicewishlist.servicies.exceptions.WishlistExceededException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    static final String CLIENT_NOT_FOUND = "Cliente não encontrado.";
    static final String PRODUCT_NOT_FOUND = "Produto não encontrado.";
    static final String WISHLIST_EXCEEDED = "A lista de desejos do cliente já possui 20 itens.";
    static final String PRODUCT_NOT_FOUND_IN_WISHLIST = "Produto não encontrado na lista de desejos do cliente.";
    static final String EXCEEDED_LIMIT = "LIMITE DE ITENS EXCEDIDO - ";

    private final WishListRepository repository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Override
    public List<WishListEntity> findAllByClientId(UUID clientId) {
        return repository.findAllByClientId(clientId);
    }

    @Override
    public WishListEntity addProductInWishListOfClient(UUID clientId, UUID productId) {
        final var client = getClientById(clientId);
        final var product = getProductById(productId);

        long currentItemCount = repository.countByClientId(clientId);
        if (currentItemCount >= 20) {
            log.info(EXCEEDED_LIMIT + WISHLIST_EXCEEDED);
            throw new WishlistExceededException(WISHLIST_EXCEEDED);
        }

        final var wishlistItem = new WishListEntity();
        wishlistItem.setId(UUID.randomUUID());
        wishlistItem.setClient(client);
        wishlistItem.setProduct(product);
        return repository.save(wishlistItem);
    }

    @Override
    public void deletProductByClientInWishList(UUID clientId, UUID productId) {
        getClientById(clientId);
        final var product = getProductById(productId);
        final var wishlistItem = repository.findByClientIdAndProductId(clientId, productId);
        if (wishlistItem.isEmpty()) {
            log.info(PRODUCT_NOT_FOUND_IN_WISHLIST);
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND_IN_WISHLIST);
        }
        log.info("Excluindo " + product.getName() + "!");
        repository.delete((WishListEntity) wishlistItem.get());
    }

    @Override
    public WishListEntity searchProductByClientInWishList(UUID clientId, String productName) {
        getClientById(clientId);

        final var wishlistItem = repository.searchProductByClientInWishList(clientId, productName);
        if (wishlistItem.isEmpty()) {
            log.info(PRODUCT_NOT_FOUND_IN_WISHLIST);
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND_IN_WISHLIST);
        }

        return (WishListEntity) wishlistItem.get();
    }

    ClientEntity getClientById(UUID clientId) {
        final var client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            log.info(CLIENT_NOT_FOUND);
            throw new ClientNotFoundException(CLIENT_NOT_FOUND);
        }
        return client.get();
    }

    private ProductEntity getProductById( UUID productId) {
        final var product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.info(PRODUCT_NOT_FOUND);
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        return product.get();
    }

}
