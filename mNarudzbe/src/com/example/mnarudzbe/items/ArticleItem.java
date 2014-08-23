package com.example.mnarudzbe.items;

public class ArticleItem {

	private String name;
	private String type;
	private String user;
	private String price;
	private int amount = 0;

	public ArticleItem(String name, String type, String price, String user) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.user = user;
	}

	public ArticleItem(String name, int amount) {
		this.name = name;
		this.amount = amount;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}