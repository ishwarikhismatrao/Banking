package com.banking.auth.servicelmpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.auth.entities.AccountDetails;
import com.banking.auth.entities.Customers;
import com.banking.auth.repository.AccountDetailsRepository;
import com.banking.auth.repository.CustomerRepository;

import com.banking.auth.service.CustomerService;
import com.banking.auth.util.AccountNumberGenerator;

@Service
public class CustomerServicelmpl implements CustomerService{
    
	@Autowired
	CustomerRepository customerRepository;
	
	
	@Autowired
	AccountDetailsRepository  accountDetailsRepository;

	
	
	@Autowired
	AccountNumberGenerator accountNumerGenerator;
	
	@Autowired
	org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
	
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	
	@Override
	public Customers registerCustomer(Customers customer) {
		
	
		String currentTime = dateFormat.format(new Date());
		
		String accountNumber = accountNumerGenerator.generateAccountNumber()+""+accountNumerGenerator.generateAccountNumber();
		
		customer.setAccountNumber(accountNumber);
		customer.setTranctionPin(""+accountNumerGenerator.generateAccountNumber());
		customer.setCreateAt(currentTime);
		customer.setUpadateAt(currentTime);
		customer.setStatus("1");
		
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		return customerRepository.save(customer);
	}

    @Override 
    public Customers findCustomerByEmail(String email) {
    	
    	return customerRepository.findCustomerByEmail(email);
    	
    }

	@Override
	public AccountDetails addAccountDetails(AccountDetails accountDetails) {
		// TODO Auto-generated method stub
		return  accountDetailsRepository.save(accountDetails);
	}

	

	@Override
	public int resetPassword(String newPassword, String email) {
		// TODO Auto-generated method stub
		return customerRepository.resetPassword(newPassword,email);
	}

	@Override
	

	public int upadateCustomers(String firstName,String middleName,String lastName,String mobile,String address ,long customerId) {
		// TODO Auto-generated method stub
		return customerRepository.updateCustomers(firstName,middleName,lastName,mobile,address ,customerId);
		}


	
	
	@Override
	public Customers fetchCustomerId(long customerId) {
		// TODO Auto-generated method stub
		return customerRepository.fetchCustomerId(customerId);
	
	}

	@Override
	public Customers fetchAccountNumber(String accountNumber) {
		// TODO Auto-generated method stub
		return customerRepository.fetchAccountNumber(accountNumber) ;
	}

	

	

}

	
    
   
	


