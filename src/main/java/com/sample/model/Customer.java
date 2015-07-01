package com.sample.model;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.sample.service.util.BankingUtil;

public class Customer {
	
	private String firstName;
	private String lastName;
	private Date dateOfBirth = new Date();
	private int uuid;
	private String email;
	private Address address; // address MUST
	private String phoneNumber;
	private Collection<Account> accounts; // accounts

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public String toString() {
		return String.format("Customer:{%s, DOB:%s; email:%s; address:%s; phoneNumber:%s; accounts:{%s}}", 
				firstName == null ? "[]" : this.getFullName(), 
				dateFormat.format(dateOfBirth),
				email == null ? "[]" : email,
				address == null ? "[]" : address.toString(),
				phoneNumber == null ? "[]" : phoneNumber,
				BankingUtil.getArrayString(accounts));
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Collection<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}
	
}
