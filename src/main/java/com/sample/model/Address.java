package com.sample.model;

public class Address {
	
	private String addressLine1;
	private String addressLine2;
	private String postalCode;
	private String city;
	private String country;
	private int uuid;
	
	public String toString() {
		return String.format("Address:{addressLine1:%s; addressLine2:%s; postalCode:%s; city:%s; country:%s}", 
				addressLine1 == null ? "[]" : addressLine1, 
				addressLine2 == null ? "[]" : addressLine2, 
				postalCode == null ? "[]" : postalCode,
				city == null ? "[]" : city,
				country == null ? "[]" : country);
	}
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	
}
