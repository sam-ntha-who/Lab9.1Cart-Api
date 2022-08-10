package co.grandcircus.cartapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.grandcircus.cartapi.models.CartItems;

public interface ItemRepository extends MongoRepository<CartItems, String> {
	List<CartItems> findAll();
	Optional<CartItems> findById(String id);
}
