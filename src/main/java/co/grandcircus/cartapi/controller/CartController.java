package co.grandcircus.cartapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.grandcircus.cartapi.exceptions.ItemNotFoundException;
import co.grandcircus.cartapi.models.CartItems;
import co.grandcircus.cartapi.repository.ItemRepository;

@RestController
public class CartController {

	@Autowired
	private ItemRepository itemRepo;
	
	@GetMapping("/reset")
	public String reset() {
		// Delete all
		itemRepo.deleteAll();
		
		// Add items
		CartItems items = new CartItems("Quick Oats", 3.29, 2);
		itemRepo.insert(items);
		
		items = new CartItems("Oat Milk", 2.89, 2);
		itemRepo.insert(items);
		
		items = new CartItems("X-Large Eggs", 3.59, 1);
		itemRepo.insert(items);
		
		items = new CartItems("Yellow Mustard", 1.58, 1);
		itemRepo.insert(items);
		
		items = new CartItems("Peanut Butter", 3.45, 3);
		itemRepo.insert(items);
		
		items = new CartItems("Chocolate Protein Powder", 23.99, 1);
		itemRepo.insert(items);
		
		items = new CartItems("Manzanilla Olives", 2.29, 3);
		itemRepo.insert(items);
		
		items = new CartItems("Classic Roast Ground Coffee", 6.79, 2);
		itemRepo.insert(items);
		
		items = new CartItems("Egyptian Licorice Herbal Tea", 4.29, 3);
		itemRepo.insert(items);
		
		items = new CartItems("Ground Pepper", 1.89, 1);
		itemRepo.insert(items);
		
		items = new CartItems("Turkey Hot Dogs", 2.39, 1);
		itemRepo.insert(items);
		
		items = new CartItems("BBQ Sauce", 1.59, 1);
		itemRepo.insert(items);
		
		items = new CartItems("Brown Sugar", 1.39, 1);
		itemRepo.insert(items);
		
		
		return "Data reset.";
	}
	@GetMapping("/cart-items")
	public List<CartItems> getCartItems() {
		// response json array of all cart items
		return itemRepo.findAll();
		
	}
	
	@GetMapping("/cart-items/{id}")
	public CartItems getOne(@PathVariable("id") String id) {
		return itemRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
 	}
	
	@PostMapping("/cart-items")
	@ResponseStatus(HttpStatus.CREATED)
	public CartItems create(@RequestBody CartItems item) {
		itemRepo.insert(item);
		return item;
	}
}
