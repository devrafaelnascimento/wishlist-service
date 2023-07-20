package br.com.magalu.servicewishlist.controllers;

import br.com.magalu.servicewishlist.entities.ClientEntity;
import br.com.magalu.servicewishlist.entities.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class WishListResponse {

    private UUID id;
    private ClientEntity client;
    private ProductEntity product;
}
