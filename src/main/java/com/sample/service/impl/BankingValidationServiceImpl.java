package com.sample.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.kie.internal.KnowledgeBase;
import org.kie.internal.runtime.StatelessKnowledgeSession;

import com.sample.model.Customer;
import com.sample.service.BankingInquiryService;
import com.sample.service.BankingValidationService;
import com.sample.service.ReportFactory;
import com.sample.service.ValidationReport;

@SuppressWarnings({ "deprecation", "restriction" })
public class BankingValidationServiceImpl implements BankingValidationService {

	private KnowledgeBase knowledgeBase;
	private ReportFactory reportFactory;
	private BankingInquiryService bankingInquiryService;
	
	/**
	 * validates provided customer and returns validation report
	 */
	public ValidationReport validate(Customer customer) {
		ValidationReport report = reportFactory.createValidationReport();
		
		StatelessKnowledgeSession session = knowledgeBase.newStatelessKnowledgeSession();
		session.setGlobal("validationReport", report);
		session.setGlobal("reportFactory", reportFactory);
		session.setGlobal("inquiryService", bankingInquiryService);
		
		Collection<Object> facts = this.getFacts(customer);
		session.execute(facts);
		
		return report;
	}
	
	/**
	 * @return facts that the rules will reason upon
	 */
	private Collection<Object> getFacts(Customer customer) {
		ArrayList<Object> facts = new ArrayList<Object>();
		System.out.println("getFacts:" + customer.toString());
		facts.add(customer);
		facts.add(customer.getAddress());
		facts.addAll(customer.getAccounts());
		return facts;
	}

	
}
