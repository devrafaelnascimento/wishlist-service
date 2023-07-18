package br.com.magalu.servicewishlist.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "wishlist")
public class WishListEntity {

    @Id
    private UUID id;
    private ClientEntity client;
    private ProductEntity product;

}
