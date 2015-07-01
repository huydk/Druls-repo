package com.sample.service.impl;

import java.util.Arrays;

import com.sample.service.Message;
import com.sample.service.Message.Type;
import com.sample.service.ReportFactory;
import com.sample.service.ValidationReport;

public class DefaultReportFactory implements ReportFactory {

	@Override
	public ValidationReport createValidationReport() {
		return new DefaultValidationReport();
	}

	@Override
	public Message createMessage(Type type, String messageKey, Object... context) {
		return new DefaultMessage(type, messageKey, Arrays.asList(context));
	}
	
}
