package com.sample.service;

import java.util.Set;

public interface ValidationReport {

	public Set<Message> getMessages();
	
	public Set<Message> getMessagesByType(Message.Type type);
	
	public boolean contains(String key);
	
	public boolean addMessage(Message message);
	
}
