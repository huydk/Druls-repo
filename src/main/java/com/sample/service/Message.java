package com.sample.service;

import java.util.List;

public interface Message {

	public enum Type {
		ERROR, WARNING
	}
	
	public Type getType();
	
	public String getMessageKey();
	
	public List<Object> getContextOrdered();
}
