package co.grandcircus.cartapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.grandcircus.cartapi.exceptions.ItemNotFoundException;
import co.grandcircus.cartapi.models.CartItems;
import co.grandcircus.cartapi.repository.ItemRepository;

@RestController
public class CartController {

	@Autowired
	private ItemRepository itemRepo;

	@ResponseBody
	@ExceptionHandler(ItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String itemNotFoundHandler(ItemNotFoundException ex) {
		return ex.getMessage();
	}

	@GetMapping("/reset")
	public String reset() {
		// Delete all
		itemRepo.deleteAll();

		// Add items
		CartItems items = new CartItems("quick oats", 3.29, 2);
		itemRepo.insert(items);

		items = new CartItems("oat milk", 2.89, 2);
		itemRepo.insert(items);

		items = new CartItems("eggs", 3.59, 1);
		itemRepo.insert(items);

		items = new CartItems("yellow mustard", 1.58, 1);
		itemRepo.insert(items);

		items = new CartItems("peanut butter", 3.45, 3);
		itemRepo.insert(items);

		items = new CartItems("chocolate protein powder", 23.99, 1);
		itemRepo.insert(items);

		items = new CartItems("manzanilla olives", 2.29, 3);
		itemRepo.insert(items);

		items = new CartItems("classic roast ground coffee", 6.79, 2);
		itemRepo.insert(items);

		items = new CartItems("egyptian licorice herbal tea", 4.29, 3);
		itemRepo.insert(items);

		items = new CartItems("ground pepper", 1.89, 1);
		itemRepo.insert(items);

		items = new CartItems("turkey hot dogs", 2.39, 1);
		itemRepo.insert(items);

		items = new CartItems("bbq sauce", 1.59, 1);
		itemRepo.insert(items);

		items = new CartItems("brown sugar", 1.39, 1);
		itemRepo.insert(items);

		return "Data reset.";
	}

	// C(R)UD get all items - works
	// need to add query string params (product/maxPrice/prefix/pageSize)
	
	@GetMapping("/cart-items")
	public List<CartItems> getCartItems(@RequestParam(required = false) String product, @RequestParam(required = false) Double maxPrice, @RequestParam(required = false) String prefix, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String search) {
		// response json array of all cart items
	
			// search for product matching exact name - works
			if (product != null) {
				product.equalsIgnoreCase(product);
				return itemRepo.findByProduct(product);
			// search for product by maxPrice - works
			} else if (maxPrice != null) {
				return itemRepo.findByPriceLessThan(maxPrice);
			// search for product by prefix - works
			} else if (prefix != null) {
				prefix.equalsIgnoreCase(prefix);
				return itemRepo.findByProductStartingWith(prefix);	
			// search for products by page size - works
			} else if ((pageSize != null) && (itemRepo.findAll().size() > pageSize)) {
				return itemRepo.findAll().subList(0, pageSize);
			// search for products containing string search - works
			} else if (search != null) {
				search.equalsIgnoreCase(search);
				return itemRepo.findByProductContaining(search);
			// if nothing searched, return all items - works
			} else {
				return itemRepo.findAll();
			}
		 
		
		
	}

	// C(R)UD - works
	// incl exception
	@GetMapping("/cart-items/{id}")
	public CartItems getOne(@PathVariable("id") String id) {
		return itemRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
	}

	// (C)RUD add an item - works
	@PostMapping("/cart-items")
	@ResponseStatus(HttpStatus.CREATED)
	public CartItems create(@RequestBody CartItems item) {
		itemRepo.insert(item);
		return item;
	}

	// CR(U)D update an item - works
	@PutMapping("/cart-items/{id}")
	public CartItems updateItem(@PathVariable("id") String id, @RequestBody CartItems item) {
		return itemRepo.save(item);
	}

	// CRU(D) delete an item - works
	@DeleteMapping("/cart-items/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		itemRepo.deleteById(id);
	}

	// get total cost of items - works
	@GetMapping("/cart-items/total-cost")
	public double getTotal() {
		List<CartItems> cartItems = itemRepo.findAll();
		double total = 0;

		for (CartItems item : cartItems) {
			total += item.getPrice() * item.getQuantity();
		}

		return total * 1.06;

	}

	// query params added - works
	@PatchMapping("/cart-items/{id}/add")
	public CartItems updateQuantity(@PathVariable("id") String id, @RequestParam Integer amount) {
		CartItems update = itemRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
		update.setQuantity(update.getQuantity() + amount);
		itemRepo.save(update);
		return update;
	}
}
