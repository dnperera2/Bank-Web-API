package com.nemal.BankAPI.service;

import com.nemal.BankAPI.dto.TransactionRequest;
import com.nemal.BankAPI.dto.TransactionResponseDTO;
import com.nemal.BankAPI.model.BankAccount;
import com.nemal.BankAPI.model.Transaction;
import com.nemal.BankAPI.repo.BankAccountRepository;
import com.nemal.BankAPI.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Optional<BankAccount> deposit(String accountNumber, TransactionRequest amount) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
            .map(acc -> {
                // Update account balance
                acc.setAccountBalance(acc.getAccountBalance() + amount.getAmount());
                BankAccount savedAccount = bankAccountRepository.save(acc);
                
                // Create and save transaction
                Transaction transaction = new Transaction();
                transaction.setTransactionAmount(amount.getAmount());
                transaction.setTransactionDate(java.time.LocalDateTime.now());
                transaction.setTransactionType("Deposit");
                transaction.setBankAccount(savedAccount);
                transactionRepository.save(transaction);
                
                return savedAccount;
            });
    }

    public Optional<BankAccount> withdraw(String accountNumber, TransactionRequest amt) {
        return bankAccountRepository.findByAccountNumber(accountNumber).map(acc -> {
            if (acc.getAccountBalance() - 500.0 < amt.getAmount()) {
                throw new RuntimeException("Insufficient balance (Min Balance RS 500 )");
            }
                // Update account balance
                acc.setAccountBalance(acc.getAccountBalance() - amount.getAmount());

                Transaction tx = new Transaction();
                tx.setTransactionDate(LocalDateTime.now());
                tx.setTransactionAmount(amount.getAmount());
                tx.setTransactionType("withdrawal");
                tx.setBankAccount(acc);

                transactionRepository.save(tx);
                return bankAccountRepository.save(acc);

            });
        }

    public List<TransactionResponseDTO> getTransactionHistory(String accountNumber) {
        List<Transaction> transactions =  transactionRepository.findByBankAccount_AccountNumber(accountNumber);
        return transactions.stream()
                .map(tx -> new TransactionResponseDTO(
                      tx.getTransactionDate(),tx.getTransactionType(),tx.getTransactionAmount()
                )).toList();
    }
}
