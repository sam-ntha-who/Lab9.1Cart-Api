package co.grandcircus.cartapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cartItems")
public class CartItems {

	@Id
	public String id;
	private String product;
	// mongo requires objects not primitives
	private Double price;
	private Integer quantity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CartItems(String product, Double price, Integer quantity) {
		super();
		this.product = product;
		this.price = price;
		this.quantity = quantity;
	}

	public CartItems() {

	}

}
