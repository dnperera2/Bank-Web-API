package com.nemal.BankAPI.repo;

import com.nemal.BankAPI.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
