package br.com.magalu.servicewishlist.repositories;

import br.com.magalu.servicewishlist.entities.WishListEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WishListRepository extends MongoRepository<WishListEntity, UUID> {

    List<WishListEntity> findAllByClientId(UUID clientId);

    Optional<Object> findByClientIdAndProductId(UUID clientId, UUID productId);

    @Query("{ 'client.id' : ?0, 'product.name' : ?1 }")
    Optional<Object> searchProductByClientInWishList(UUID clientId, String productName);

}
