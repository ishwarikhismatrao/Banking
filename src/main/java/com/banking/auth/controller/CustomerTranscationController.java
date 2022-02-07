package com.banking.auth.controller;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.auth.customrequest.CustomRequestForMoneyTransfer;
import com.banking.auth.customresponse.CustomerResponseForUser;
import com.banking.auth.entities.AccountDetails;
import com.banking.auth.entities.CustomerTransactions;
import com.banking.auth.entities.Customers;
import com.banking.auth.service.AccountDetailsService;
import com.banking.auth.service.CustomerService;
import com.banking.auth.service.CustomerTransactionService;
import com.banking.auth.util.Validations;

@RestController 
@RequestMapping("/customer/transactions")
public class CustomerTranscationController {
	
	@Autowired
	CustomerService customerservice;
	
	@Autowired
	AccountDetailsService  accountDetailsService; 
	
	@Autowired
	CustomerTransactionService customerTransactionsService;
	
	@Autowired 
	Validations validations;
	
	@PostMapping("/addBalance")
	public ResponseEntity<Object>addBalance(@RequestBody AccountDetails accountDetails) {
		
		validations.addBalanceValidations(accountDetails);
		
		AccountDetails fetchAccountDetails = accountDetailsService.fetchAccountDetails(accountDetails.getAccountNumber());
		if(fetchAccountDetails!=null) {
			
			long accountBalance = fetchAccountDetails.getAccountBalance()+ accountDetails.getAccountBalance();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String date = "" +dateFormat.format(new Date());
			accountDetailsService.updateAccountBalance(accountDetails.getAccountBalance(),accountDetails.getAccountNumber(),date);
			
			CustomerResponseForUser   responseStructure = new CustomerResponseForUser(new Date(),"Account Balance Updated Suceesfully","409");
			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
	
		}else {
	
			CustomerResponseForUser   responseStructure = new CustomerResponseForUser(new Date(),"Invalid Account Number","409");
			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
		
		}
	}

		
	@PostMapping("/transferMoney")
	public ResponseEntity<Object> transferMoney(@RequestBody CustomRequestForMoneyTransfer moneyTransfer ){
	AccountDetails fetchAccountDetails = accountDetailsService.fetchAccountDetails(moneyTransfer.getAccountNumber());
	AccountDetails fetchReceiversAccount = accountDetailsService.fetchAccountDetails(moneyTransfer.getUserAccountNumber());
	Customers fetchAccountNumber = customerservice.fetchAccountNumber(moneyTransfer.getAccountNumber());
	
	if(fetchAccountDetails!= null && fetchReceiversAccount != null) {
	if(moneyTransfer.getAmount() >= 0 && moneyTransfer.getAmount() <= fetchAccountDetails.getAccountBalance()) {
	if(fetchReceiversAccount.getBranchName().equals(moneyTransfer.getBranchName())) {
	if(fetchReceiversAccount.getIfsc().equals(moneyTransfer.getIfsc())) {
	if(fetchAccountNumber.getTranctionPin().equals(moneyTransfer.getTranctionPin()))
	{
	long sendersAccountBalance = fetchAccountDetails.getAccountBalance() - moneyTransfer.getAmount();
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	String date = "" +dateFormat.format(new Date());
	int sender =accountDetailsService.updateAccountBalance(sendersAccountBalance,date,fetchAccountDetails.getAccountNumber());
	
	CustomerTransactions sendersTransaction = new CustomerTransactions();
	
	sendersTransaction.setAccountNumber(fetchAccountDetails.getAccountNumber());
	sendersTransaction.setAmount(moneyTransfer.getAmount());
	sendersTransaction.setUserAccountNumber(moneyTransfer.getUserAccountNumber());
	sendersTransaction.setStatus("Debit");
	sendersTransaction.setCreateAt(date);
	sendersTransaction.setUpadateAt(date);
	
	customerTransactionsService.saveTransaction(sendersTransaction);
	
	long receiversAccountBalance = fetchReceiversAccount.getAccountBalance() + moneyTransfer.getAmount();
	accountDetailsService.updateAccountBalance(receiversAccountBalance,date,fetchReceiversAccount.getAccountNumber()); 
	CustomerTransactions receiversTransaction = new CustomerTransactions();
	receiversTransaction.setAccountNumber(fetchReceiversAccount.getAccountNumber());
	receiversTransaction.setAmount(moneyTransfer.getAmount());
	receiversTransaction.setUserAccountNumber(moneyTransfer.getAccountNumber());
	receiversTransaction.setStatus("Credit");
	receiversTransaction.setCreateAt(date);
	receiversTransaction.setUpadateAt(date);
	
	customerTransactionsService.saveTransaction(receiversTransaction);
	
	CustomerResponseForUser responseStructure = new CustomerResponseForUser(new Date(),"Money Transfered Successsfully","200");
	return new ResponseEntity<Object> (responseStructure ,HttpStatus.OK);
	}
	else {
	CustomerResponseForUser responseStructure = new CustomerResponseForUser(new Date(),"Invalid Transaction Pin","409");
	return new ResponseEntity<Object> (responseStructure ,HttpStatus.OK);
	}
	}
	else {
	CustomerResponseForUser responseStructure = new CustomerResponseForUser(new Date(),"Invalid IFSC","409");
	return new ResponseEntity<Object> (responseStructure ,HttpStatus.OK);
	}
	}
	else {
	CustomerResponseForUser responseStructure = new CustomerResponseForUser(new Date(),"Invalid Branch Name","409");
	return new ResponseEntity<Object> (responseStructure ,HttpStatus.OK);
	}
	}
	else {
	CustomerResponseForUser responseStructure = new CustomerResponseForUser(new Date(),"Enter Valid Amount you dont have enough amount","409");
	return new ResponseEntity<Object> (responseStructure ,HttpStatus.OK);
	}
	}
	else {
	CustomerResponseForUser responseStructure = new CustomerResponseForUser(new Date(),"Enter proper account details user doesn't exist","409");
	return new ResponseEntity<Object> (responseStructure ,HttpStatus.OK);
	}
	}
	}

