package com.sample.service.impl;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
//import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sample.service.Message;
import com.sample.service.util.BankingUtil;

public class DefaultMessage implements Message {

	private Message.Type type;
	private String messageKey;
	private List<Object> context;
	
	public DefaultMessage(Message.Type type, String messageKey, List<Object> context) {
		if (type == null || messageKey == null) {
			throw new IllegalArgumentException("type and messageKey cannot be null");
		}
		
		this.type = type;
		this.messageKey = messageKey;
		this.context = context;
	}
	
	@Override
	public String toString() {
		return String.format("DefaultMessage:{type=%s; messageKey:%s; context:{%s}}", 
				type.toString(), 
				messageKey,
				BankingUtil.getArrayString(context));
	}
	
	@Override
	public Message.Type getType() {
		return type;
	}

	@Override
	public String getMessageKey() {
		return messageKey;
	}

	@Override
	public List<Object> getContextOrdered() {
		return context;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		
		if (!(other instanceof DefaultMessage))
			return false;
		
		DefaultMessage castOther = (DefaultMessage) other;
		return new EqualsBuilder().append(type, castOther.type).append(messageKey, castOther.messageKey).append(context, castOther.context).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(98587969, 810426655).append(type).append(messageKey).append(context).toHashCode();
	}
	
//	@Override
//	public String toString() {
//		return new ToStringBuilder(this).append("type", type).append("messageKey", messageKey).append("context", context).toString();
//	}
}
