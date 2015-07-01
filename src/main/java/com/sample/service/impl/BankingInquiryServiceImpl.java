package com.sample.service.impl;

import com.sample.model.Account;
import com.sample.service.BankingInquiryService;

public class BankingInquiryServiceImpl implements BankingInquiryService {

	
	public boolean isAccountNumberUnique (Account account) {
		return true;
	}
}
