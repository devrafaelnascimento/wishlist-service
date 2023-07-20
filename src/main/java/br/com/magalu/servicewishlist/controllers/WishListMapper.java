package br.com.magalu.servicewishlist.controllers;

import br.com.magalu.servicewishlist.entities.WishListEntity;
import org.springframework.stereotype.Component;

@Component
public class WishListMapper {

    public WishListResponse toResponse(WishListEntity wishList) {
        final var response = new WishListResponse();
        response.setId(wishList.getId());
        response.setClient(wishList.getClient());
        response.setProduct(wishList.getProduct());
        return response;
    }
}
