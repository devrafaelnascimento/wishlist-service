package br.com.magalu.servicewishlist.servicies.impl;

import br.com.magalu.servicewishlist.entities.ClientEntity;
import br.com.magalu.servicewishlist.servicies.WishListService;
import br.com.magalu.servicewishlist.entities.WishListEntity;
import br.com.magalu.servicewishlist.repositories.ClientRepository;
import br.com.magalu.servicewishlist.repositories.ProductRepository;
import br.com.magalu.servicewishlist.repositories.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository repository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Override
    public List<WishListEntity> findAllByClientId(UUID clientId) {
        return repository.findAllByClientId(clientId);
    }

    @Override
    public WishListEntity addProductInWishListOfClient(UUID clientId, UUID productId) {
        return clientRepository.findById(clientId)
                .map(client -> productRepository.findById(productId)
                        .map(product -> {
                            WishListEntity wishlistItem = new WishListEntity();
                            wishlistItem.setId(UUID.randomUUID());
                            wishlistItem.setClient(client);
                            wishlistItem.setProduct(product);
                            return repository.save(wishlistItem);
                        })
                        .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"))
                )
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }

    @Override
    public void deletProductByClientInWishList(UUID clientId, UUID productId) {
        getClientById(clientId);

        final var wishlistItem = repository.findByClientIdAndProductId(clientId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado na wishlist do cliente"));

        repository.delete((WishListEntity) wishlistItem);
    }

    @Override
    public WishListEntity searchProductByClientInWishList(UUID clientId, String productName) {
        getClientById(clientId);

        final var wishlistItem = repository.searchProductByClientInWishList(clientId, productName)

                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado na wishlist do cliente"));
        return (WishListEntity) wishlistItem;
    }

    private ClientEntity getClientById(UUID clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }

}
