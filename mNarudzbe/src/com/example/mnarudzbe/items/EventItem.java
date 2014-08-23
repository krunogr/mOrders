package com.example.mnarudzbe.items;

public class EventItem {

	private String place;
	private String datum_eventa;
	private String name;
	private String description;

	public EventItem(String place, String datum_eventa, String name,
			String description) {
		this.place = place;
		this.datum_eventa = datum_eventa;
		this.name = name;
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDatum_eventa() {
		return datum_eventa;
	}

	public void setDatum_eventa(String datum_eventa) {
		this.datum_eventa = datum_eventa;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}