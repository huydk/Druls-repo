package com.sample.model;

import java.math.BigDecimal;
import java.util.Date;

public class Account {

	public enum Type {
		TRANSACTIONAL, STUDENT
	}
	
	private int number;
	private String name;
	private BigDecimal balance = BigDecimal.ZERO;
	private String currency;
	private Date startDate;
	private Date endDate;
	private Type type;
	private BigDecimal interestRate;
	private int uuid;
	private String status;
	private Customer owner;
	
	public Account() {
		type = Type.TRANSACTIONAL;
	}
	
	public String toString() {
		return String.format("Account{no:%s, name:%s; balance:%s; type:%s; owner:%s}", 
				number, 
				name == null ? "[]" : name,
				balance == null ? "[]" : balance.toString(),
				type.toString(),
				owner == null ? "[]" : owner.getFullName());
		
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Customer getOwner() {
		return owner;
	}
	public void setOwner(Customer owner) {
		this.owner = owner;
	}
	
}
