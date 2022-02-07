package com.banking.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.auth.entities.CustomerTransactions;
import com.banking.auth.entities.Customers;


@Repository
public interface CustomerTransactionRepository extends JpaRepository<CustomerTransactions,Long> {

	Customers save(String accountNumber);

}
