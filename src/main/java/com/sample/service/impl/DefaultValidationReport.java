package com.sample.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sample.service.Message;
import com.sample.service.ValidationReport;

public class DefaultValidationReport implements ValidationReport {

	protected Map<Message.Type, Set<Message>> messagesMap =
			new HashMap<Message.Type, Set<Message>>();
	
	@Override
	public Set<Message> getMessages() {
		Set<Message> messagesAll = new HashSet<Message>();
		for (Collection<Message> messages : messagesMap.values()){
			messagesAll.addAll(messages);
		}
		return messagesAll;
	}

	@Override
	public Set<Message> getMessagesByType(Message.Type type) {
		if (type == null)
			return Collections.emptySet();
		
		Set<Message> messages = messagesMap.get(type);
		if (messages == null)
			return Collections.emptySet();
		else
			return messages;
	}

	@Override
	public boolean contains(String messageKey) {
		for (Message message : getMessages()) {
			if (messageKey.equals(message.getMessageKey())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addMessage(Message message) {
		if (message == null)
			return false;
		
		Set<Message> messages =messagesMap.get(message.getType());
		if (messages == null) {
			messages = new HashSet<Message>();
			messagesMap.put(message.getType(), messages);
		}
		return messages.add(message);
	}

}
