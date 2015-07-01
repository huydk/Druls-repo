package com.sample.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.command.Command;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.conf.SequentialOption;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatelessKnowledgeSession;

import com.sample.model.Account;
import com.sample.model.Address;
import com.sample.model.Customer;
import com.sample.service.BankingInquiryService;
import com.sample.service.Message;
import com.sample.service.ReportFactory;
import com.sample.service.ValidationReport;
import com.sample.service.impl.BankingInquiryServiceImpl;
import com.sample.service.impl.DefaultReportFactory;

@SuppressWarnings({ "deprecation", "restriction" })
public class ValidationTest {

	static StatelessKnowledgeSession session;
	static ReportFactory reportFactory;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("setUpClass - START");
		KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		builder.add(ResourceFactory.newClassPathResource("rules/Validation.drl"), ResourceType.DRL);
		if (builder.hasErrors()) {
			throw new RuntimeException(builder.getErrors().toString());
		}
		KieBaseConfiguration configuration = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		configuration.setOption(SequentialOption.YES);
		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase(configuration);
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
		
		BankingInquiryService inquiryService = new BankingInquiryServiceImpl();
		reportFactory = new DefaultReportFactory();
		
		session = knowledgeBase.newStatelessKnowledgeSession();
		session.setGlobal("reportFactory", reportFactory);
		session.setGlobal("inquiryService", inquiryService);
		
		System.out.println("setUpClass - END");
	}
	
	@SuppressWarnings("unchecked")
	private void assertNotReportContains(Message.Type type, String messageKey, Customer customer, Object... context) {
		System.out.println("assertNotReportContains - START");
		
		// create report
		ValidationReport report = reportFactory.createValidationReport();
		
		List<Command<Void>> commands = new ArrayList<Command<Void>>();
		
		commands.add(CommandFactory.newSetGlobal("validationReport", report));
		commands.add(CommandFactory.newInsertElements(getFacts(customer)));
		
//		System.out.println("assertNotReportContains - about to execute validation...");
		session.execute(CommandFactory.newBatchExecution(commands));
		
		// become error in case not receive corresponding validation error
		assertTrue("Report doesn contain message [" + messageKey+ "]", !report.contains(messageKey));
		Message message = getMessage(report, messageKey);
		if (message != null) {
			System.out.println("message=" + message.toString());
			
			List<Object> contextList = Arrays.asList(context);
			List<Object> orderedList = message.getContextOrdered();
			
			// parameter list should be same
			assertEquals(contextList, orderedList);
		}

		
		System.out.println("assertNotReportContains - END");
	}

	/**
	 * 
	 * @param type level of error in case validation fail
	 * @param messageKey validation name
	 * @param customer object to validate
	 * @param context other objects for messaging
	 */
	@SuppressWarnings({ "unchecked" })
	private void assertReportContains(Message.Type type, String messageKey, Customer customer, Object... context) {
		System.out.println("assertReportContains - START");
		
		// create report
		ValidationReport report = reportFactory.createValidationReport();
		
		List<Command<Void>> commands = new ArrayList<Command<Void>>();
		
		commands.add(CommandFactory.newSetGlobal("validationReport", report));
		commands.add(CommandFactory.newInsertElements(getFacts(customer)));
		
//		System.out.println("assertReportContains - about to execute validation...");
		session.execute(CommandFactory.newBatchExecution(commands));
		
		// become error in case not receive corresponding validation error
		assertTrue("Report doesn't contain message [" + messageKey+ "]", report.contains(messageKey));
		Message message = getMessage(report, messageKey);
		
		if (message != null) {
			System.out.println("message=" + message.toString());
			
			List<Object> contextList = Arrays.asList(context);
			List<Object> orderedList = message.getContextOrdered();
			
			// parameter list should be same
			assertEquals(contextList, orderedList);
		}
		
		System.out.println("assertReportContains - END");
	}
	
	private Message getMessage(ValidationReport report, String messageKey) {
		for (Message message : report.getMessages()) {
			if (messageKey.equals(message.getMessageKey())) {
				return message;
			}
		}
		return null;
	}

	/**
	 * Get facts
	 * 
	 * @param customer
	 * @return facts about Customer, Customer's addresses, Customer's accounts
	 */
	private Collection<Object> getFacts(Customer customer) {
		ArrayList<Object> facts = new ArrayList<Object>();
		System.out.println("getFacts:" + customer.toString());
		facts.add(customer);
		facts.add(customer.getAddress());
		facts.addAll(customer.getAccounts());
		return facts;
	}

	/**
	 * Create a dummy customer
	 * @return Dummy customer
	 */
	private Customer createCustomerBasic(String fname, boolean withAddress, boolean withAccount) {
		Customer customer = new Customer();
		customer.setFirstName(fname);
		customer.setLastName("Smith");
		customer.setPhoneNumber("(+84)903-999-889 ");
		
		if (withAccount) {
			Account account = new Account();
			account.setOwner(customer);
			List<Account> accList = new ArrayList<Account>();
			accList.add(account);
			customer.setAccounts(accList);
		}
		
		if (withAddress) {
			Address address = new Address();
			address.setAddressLine1("999 High Avenue, LS");
			customer.setAddress(address);
		}
		
		return customer;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void addressRequired() throws Exception {
		System.out.println("@Test addressRequired - START");
		
		Customer customer = createCustomerBasic("Abraham", false, true);
		
		Account account = customer.getAccounts().iterator().next();
		account.setBalance(new BigDecimal(1000));
		
		assertNull(customer.getAddress());
		
		assertReportContains(Message.Type.WARNING, "addressRequired", customer);
		
		customer.setAddress(new Address());
		assertNotReportContains(Message.Type.WARNING, "addressRequired", customer);
		
		System.out.println("@Test addressRequired - END");
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void accountBalanceAtLeast() throws Exception {
		System.out.println("@Test accountBalanceAtLeast - START");
		
		Customer customer = createCustomerBasic("Bob", true, true);
		Account account =customer.getAccounts().iterator().next();
		
		assertEquals(BigDecimal.ZERO, account.getBalance());
		
		assertReportContains(Message.Type.WARNING, "accountBalanceAtLeast", customer, account);
		
		account.setBalance(new BigDecimal("54.00"));
		assertReportContains(Message.Type.WARNING, "accountBalanceAtLeast", customer, account);
		
		account.setBalance(new BigDecimal("122.34"));
		assertNotReportContains(Message.Type.WARNING, "accountBalanceAtLeast", customer);
		
		System.out.println("@Test accountBalanceAtLeast - END");
	}
	
//	/**
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testDummy() throws Exception {
//		System.out.println("TEST RUN!!! - START");
//		
//		//setUpClass();
//		
//		Customer customer = createCustomerBasic();
//		System.out.println(customer.toString());
//		// make sure customer's address is blank
//		assertNull(customer.getAddress());
//		
//		customer.getAccounts().add(new Account());
//		
//		Account account = customer.getAccounts().iterator().next();
//		account.setOwner(customer);
//		System.out.println(account.toString());
//		// make sure account balance is zero
//		assertEquals(BigDecimal.ZERO, account.getBalance());
//		
//		assertReportContains(Message.Type.WARNING, "addressRequired", customer);
//		
//		System.out.println("TEST RUN!!! - END");
//	}
	
	@Test
	public void studentAccountCustomerAgeLessThan() throws Exception {
		System.out.println("@Test studentAccountCustomerAgeLessThan - START");
		LocalDate NOW = new LocalDate();
		
		Customer customer = createCustomerBasic("Stuart", true, true);
		customer.setDateOfBirth(NOW.minusYears(40).toDate());
		
		Account account = customer.getAccounts().iterator().next();
		account.setBalance(new BigDecimal(1000));
		
		// expecting OK validation
		assertEquals(Account.Type.TRANSACTIONAL, account.getType());
		assertNotReportContains(Message.Type.ERROR, "studentAccountCustomerAgeLessThan", customer);
		
		// expecting NG validation
		account.setType(Account.Type.STUDENT);
		assertReportContains(Message.Type.ERROR, "studentAccountCustomerAgeLessThan",customer,account);
		
		// expecting OK validation
		customer.setDateOfBirth(NOW.minusYears(20).toDate());
		assertNotReportContains(Message.Type.ERROR, "studentAccountCustomerAgeLessThan", customer);
	
		System.out.println("@Test studentAccountCustomerAgeLessThan - END");
	}
	
	@Test
	public void accountNumberUnique() throws Exception {
		System.out.println("@Test accountNumberUnique - START");
		
		Customer customer = createCustomerBasic("Unix", true, true);
		
		Account account = customer.getAccounts().iterator().next();
		account.setBalance(new BigDecimal(1000));
		
		session.setGlobal("inquiryService", new BankingInquiryServiceImpl());
		assertNotReportContains(Message.Type.ERROR, "accountNumberUnique", customer, account);
		
		System.out.println("@Test accountNumberUnique - END");
	}
	
}
