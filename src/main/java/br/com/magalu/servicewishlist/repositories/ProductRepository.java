package br.com.magalu.servicewishlist.repositories;

import br.com.magalu.servicewishlist.entities.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ProductRepository extends MongoRepository<ProductEntity, UUID> {
}
