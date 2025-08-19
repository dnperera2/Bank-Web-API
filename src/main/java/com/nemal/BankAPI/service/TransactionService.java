package com.nemal.BankAPI.service;

import aj.org.objectweb.asm.commons.Remapper;
import com.nemal.BankAPI.model.BankAccount;
import com.nemal.BankAPI.model.Transaction;
import com.nemal.BankAPI.repo.BankAccountRepository;
import com.nemal.BankAPI.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Optional<BankAccount> deposit(String accountNumber, double amount) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
            .map(acc -> {
                // Update account balance
                acc.setAccountBalance(acc.getAccountBalance() + amount);
                BankAccount savedAccount = bankAccountRepository.save(acc);
                
                // Create and save transaction
                Transaction transaction = new Transaction();
                transaction.setTransactionAmount(amount);
                transaction.setTransactionDate(java.time.LocalDate.now());
                transaction.setTransactionType("Deposit");
                transaction.setBankAccount(savedAccount);
                transactionRepository.save(transaction);
                
                return savedAccount;
            });
    }

    public Optional<BankAccount> withdraw(String accountNumber, double amount) {
        return bankAccountRepository.findByAccountNumber(accountNumber).map(acc -> {
            if (acc.getAccountBalance() - 500.0 < amount) {
                throw new RuntimeException("Insufficient balance (Min Balance RS 500 )");
            }
                // Update account balance
                acc.setAccountBalance(acc.getAccountBalance() - amount);

                Transaction tx = new Transaction();
                tx.setTransactionDate(LocalDate.now());
                tx.setTransactionAmount(amount);
                tx.setTransactionType("withdrawal");
                tx.setBankAccount(acc);

                transactionRepository.save(tx);
                return bankAccountRepository.save(acc);

            });
        }
}
