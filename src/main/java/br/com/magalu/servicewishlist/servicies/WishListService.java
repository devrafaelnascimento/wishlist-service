package br.com.magalu.servicewishlist.servicies;

import br.com.magalu.servicewishlist.entities.WishListEntity;

import java.util.List;
import java.util.UUID;

public interface WishListService {

    List<WishListEntity> findAllByClientId(UUID clientId);

    WishListEntity addProductInWishListOfClient(UUID clientId, UUID productId);

    void deletProductByClientInWishList(UUID clientId, UUID productId);

    WishListEntity searchProductByClientInWishList(UUID clientId, String productName);

}
