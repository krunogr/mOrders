package com.example.mnarudzbe.items;

public class ProductItem {
	String type, name;
	float price;
	int id;

	public ProductItem(String type, String name, float price) {
		super();
		this.type = type;
		this.name = name;
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
