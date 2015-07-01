package com.sample.service;

public interface ReportFactory {
	
	public ValidationReport createValidationReport();
	
	public Message createMessage(Message.Type type, String messageKey, Object... context);
}
