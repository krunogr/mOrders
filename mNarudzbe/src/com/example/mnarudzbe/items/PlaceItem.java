package com.example.mnarudzbe.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class used to represent a image inside the gallery view Contains an image and
 * a text below
 * 
 * @author javatechig {@link http://javatechig.com}
 */
public class PlaceItem implements Parcelable {
	private String address;
	private String name;
	private String email;
	private String user;

	public PlaceItem(String name, String address, String email, String user) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.user = user;
	}

	public PlaceItem(Parcel source) {
		address = source.readString();
		name = source.readString();
		email = source.readString();
		user = source.readString();
	}

	public static final Parcelable.Creator<PlaceItem> CREATOR = new Parcelable.Creator<PlaceItem>() {
		public PlaceItem createFromParcel(Parcel in) {
			return new PlaceItem(in);
		}

		public PlaceItem[] newArray(int size) {
			return new PlaceItem[size];
		}
	};

	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int describeContents() {
		return 0;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(address);
		dest.writeString(email);
		dest.writeString(user);
	}
}
