package com.banking.auth.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.auth.customrequest.CustomOtpRequest;
import com.banking.auth.customresponse.CustomRequestForResetPassword;
import com.banking.auth.customresponse.CustomResponseForAccountDetails;
import com.banking.auth.customresponse.CustomerResponseForCustomerRegister;
import com.banking.auth.customresponse.CustomerResponseForUser;
import com.banking.auth.entities.AccountDetails;
import com.banking.auth.entities.Customers;
import com.banking.auth.service.AccountDetailsService;
import com.banking.auth.service.CustomerService;
import com.banking.auth.util.AccountNumberGenerator;
import com.banking.auth.util.MailService;
import com.banking.auth.util.Validations;


@RestController
@RequestMapping("/Customer/auth")
public class CustomerAuthenticationCustomer {
	
	@Autowired
    CustomerService customerservice;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AccountDetailsService  accountDetailsService; 
	@Autowired 
	Validations validations;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	AccountNumberGenerator accountNumberGenerator;
	
	@PostMapping("/resigsterCustomer")
	public ResponseEntity<Object> resigsterCustomer(@RequestBody Customers customer){
		
		validations.registerCustomer(customer);
		
		Customers findCustomer = customerservice .findCustomerByEmail(customer.getEmail());
		if(findCustomer == null) {
			Customers registerCustomer = customerservice.registerCustomer(customer);
			
			AccountDetails  accountDetails = new AccountDetails();
			
			accountDetails.setAccountNumber(registerCustomer.getAccountNumber());
			accountDetails.setBranchName("pune");
			accountDetails.setCustomerId(registerCustomer);
			accountDetails.setAccountBalance(0);
			accountDetails.setCreatedAt(registerCustomer.getCreateAt());
			accountDetails.setUpadteAt(registerCustomer.getUpadateAt());
			accountDetails.setIfsc("PARKAR2222");
			accountDetails.setStatus(registerCustomer.getStatus());
			
			customerservice.addAccountDetails(accountDetails);
			
	
			
			
			
			
			
			
			
			CustomerResponseForCustomerRegister responseStructure = new CustomerResponseForCustomerRegister(new Date()
					,"customer create successfully","200",registerCustomer);
			
			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
		}else {
			CustomerResponseForUser   responseStructure = new CustomerResponseForUser(new Date(),"Customer already Registered","409");
			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
			
		}
		}
		@PostMapping("/loginCustomer")
		public ResponseEntity<Object> loginCustomer(@RequestBody  Customers customer){
			
			validations.loginCustomers(customer);
			
		Customers findCustomer = customerservice .findCustomerByEmail(customer.getEmail());
		if(findCustomer != null) {
			
			
			
			if(encoder.matches(customer.getPassword(),findCustomer.getPassword()) == true) {
				 CustomerResponseForUser responseStructure = new CustomerResponseForUser(new Date()
					,"login Successful..!","200");
			
			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
		
			
		}else {
		
			CustomerResponseForUser   responseStructure = new CustomerResponseForUser(new Date(),"Invalid Credentials","409");
			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
			
		}
		}else {
			CustomerResponseForUser   responseStructure = new CustomerResponseForUser(new Date(),"Customer not found","409");
			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
			
}
		
}
		
		@PostMapping ("/fetchAccountDetails")
		public ResponseEntity<Object> fetchAccountDetails(@RequestBody  AccountDetails accountDetails){
			
			AccountDetails accountDetail = accountDetailsService.fetchAccountDetails(accountDetails.getAccountNumber());
			
			
			if(accountDetail != null) {
				CustomResponseForAccountDetails   responseStructure = new CustomResponseForAccountDetails(new Date(),"Account details fetch successfully","200", accountDetail);
				return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
				
		
				}else {
				CustomerResponseForUser   responseStructure = new CustomerResponseForUser(new Date(),"Account not found","409");
				return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
				
		}

		
		
		}

		
	@PostMapping("/sendOtpOnEmail")
	public ResponseEntity<Object> sendOtpOnEmail(@RequestBody  CustomOtpRequest customer){
		
		Customers findCustomer = customerservice .findCustomerByEmail(customer.getEmail());
		
		if(findCustomer != null) {
			
			
			mailService.senMail(customer.getEmail(),accountNumberGenerator.generateTransactionPin());
		
			CustomerResponseForUser  responseStructure = new CustomerResponseForUser(new Date(),"OTP sent on Email","200");
			     return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
			
	
			}else {
			CustomerResponseForUser   responseStructure = new CustomerResponseForUser(new Date(),"Mail id not found","409");
			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
	
	
}
}

	
	      @PostMapping("/UpadateCustomer")
	      public ResponseEntity<Object>UpadateCustomer (@RequestBody  Customers customer){
	  		
	    	  
	  		Customers customeraccount = customerservice .fetchCustomerId(customer.getCustomerId());
	  		
	  		if(customeraccount!= null) {
	  			  
	  			customeraccount.setFirstName(customer.getFirstName());
	  			customeraccount.setMiddleName(customer.getMiddleName());
	  			customeraccount.setLastName(customer.getLastName());
	  			customeraccount.setMobile(customer.getMobile());
	  			customeraccount.setAddress(customer.getAddress());
	  			
	  			
	  			customerservice.upadateCustomers(customer.getFirstName(),customer.getMiddleName(),customer.getLastName(),customer.getMobile(),customer.getAddress(),customer.getCustomerId());
	  		
	  			CustomerResponseForUser  responseStructure = new CustomerResponseForUser(new Date(),"Upadate successfully","200");
	  			     return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
	  			
	  	
	  			}else {
	  			CustomerResponseForUser   responseStructure = new CustomerResponseForUser(new Date(),"customer id not found","409");
	  			return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
	  	
	  	
	  }
	  }
	      
	
	@PostMapping("/ResetPassword")
	 public ResponseEntity<Object>ResetPassword (@RequestBody  CustomRequestForResetPassword customer){
	 
		 Customers findCustomer= customerservice .findCustomerByEmail(customer.getEmail());
			
			if(findCustomer!= null) {
				if(customer.getPassword().equals(customer.getNewPassword()))
				{
			if(encoder.matches(customer.getNewPassword(),findCustomer.getPassword())==true)
			{
			
	
			CustomerResponseForUser  responseStructure = new CustomerResponseForUser(new Date(),"Password upadate successfully","200");
		     return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
	 }else {
		 
		 int n=customerservice.resetPassword(customer.getNewPassword(),customer.getEmail());
	 CustomerResponseForUser  responseStructure = new CustomerResponseForUser(new Date(),"password does not match","409");
     return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
     
	 }}
				else {
					CustomerResponseForUser  responseStructure = new CustomerResponseForUser(new Date(),"Record not found","409");
				     return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
}	}else {
				CustomerResponseForUser  responseStructure = new CustomerResponseForUser(new Date(),"password reset successfully","200");
			     return new ResponseEntity<Object>(responseStructure,HttpStatus.OK);
			}
	 }

}




		

