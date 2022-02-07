package com.banking.auth.service;

import com.banking.auth.entities.AccountDetails;


public interface AccountDetailsService  {


	AccountDetails fetchAccountDetails(String accountDetails);

	int updateAccountBalance(long accountBalance,String accountNumber, String date);

}
	
	
	
	

