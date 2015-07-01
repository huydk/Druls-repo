package com.sample.service;

import com.sample.model.Customer;

/**
 * service for validating the banking domain
 */
public interface BankingValidationService {

	/**
	 * validates given customer and returns validation report
	 */
	public ValidationReport validate(Customer customer);
}
