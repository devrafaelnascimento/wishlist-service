package br.com.magalu.servicewishlist.repositories;

import br.com.magalu.servicewishlist.entities.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ClientRepository extends MongoRepository<ClientEntity, UUID> {
}
