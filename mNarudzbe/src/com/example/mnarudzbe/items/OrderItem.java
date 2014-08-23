package com.example.mnarudzbe.items;

import java.util.ArrayList;

public class OrderItem {

	private String time;
	private ArrayList<ArticleItem> listForOrder;
	private String user;
	private String price;
	private String nameOfPlace;

	public OrderItem(String time, ArrayList<ArticleItem> listForOrder,
			String user, String price, String nameOfPlace) {
		super();
		this.time = time;
		this.listForOrder = listForOrder;
		this.user = user;
		this.price = price;
		this.nameOfPlace = nameOfPlace;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ArrayList<ArticleItem> getListForOrder() {
		return listForOrder;
	}

	public void setListForOrder(ArrayList<ArticleItem> listForOrder) {
		this.listForOrder = listForOrder;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNameOfPlace() {
		return nameOfPlace;
	}

	public void setNameOfPlace(String nameOfPlace) {
		this.nameOfPlace = nameOfPlace;
	}

}
