package com.sample.service;


import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.kie.api.runtime.rule.RuleContext;
import org.kie.internal.runtime.KnowledgeRuntime;

@SuppressWarnings("restriction")
public class ValidationHelper {

	@SuppressWarnings({ "deprecation" })
	public static void error(RuleContext kcontext, Object... context) {
		System.out.println("error - START");
		
		KnowledgeRuntime knowledgeRuntime = (KnowledgeRuntime) kcontext.getKnowledgeRuntime();
		ValidationReport validationReport = (ValidationReport) knowledgeRuntime.getGlobal("validationReport");
		ReportFactory reportFactory = (ReportFactory) knowledgeRuntime.getGlobal("reportFactory");
		
		validationReport.addMessage(
				reportFactory.createMessage(
						Message.Type.ERROR,
						kcontext.getRule().getName(),
						context));
		
		System.out.println("ruleName=" + kcontext.getRule().getName());
		System.out.println("error - END");
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void warning(RuleContext kcontext, Object... context) {
		System.out.println("warning - START");
		
		KnowledgeRuntime knowledgeRuntime = (KnowledgeRuntime) kcontext.getKnowledgeRuntime();
		ValidationReport validationReport = (ValidationReport) knowledgeRuntime.getGlobal("validationReport");
		ReportFactory reportFactory = (ReportFactory) knowledgeRuntime.getGlobal("reportFactory");
		
		validationReport.addMessage(
				reportFactory.createMessage(
						Message.Type.WARNING,
						kcontext.getRule().getName(),
						context));
		
		System.out.println("ruleName=" + kcontext.getRule().getName());
		System.out.println("warning - END");
	}
	
	/**
	* @return number of years between today and specified date
	*/
	public static int yearsPassedSince(Date date) {
		return Years.yearsBetween(new LocalDate(date), new LocalDate()).getYears();
	}
}
