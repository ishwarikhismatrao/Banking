package com.banking.auth.service;

import com.banking.auth.entities.AccountDetails;
import com.banking.auth.entities.Customers;

public interface CustomerService {
   
	Customers registerCustomer (Customers customer);

	
	
	AccountDetails addAccountDetails(AccountDetails accountDetails);

	
	int resetPassword(String newPassword, String email);

   Customers findCustomerByEmail(String email);
   Customers fetchCustomerId(long customerId);



    


	int upadateCustomers(String firstName, String middleName, String lastName, String mobile, String address,
			long customerId);



	Customers fetchAccountNumber(String accountNumber);



}



	



