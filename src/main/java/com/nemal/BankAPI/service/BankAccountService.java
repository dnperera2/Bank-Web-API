package com.nemal.BankAPI.service;

import aj.org.objectweb.asm.commons.Remapper;
import com.nemal.BankAPI.model.BankAccount;
import com.nemal.BankAPI.repo.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private static final List<String> VALID_ACCOUNT_TYPES = List.of("savings", "current");

    @Autowired
    private BankAccountRepository bankAccountRepository;

    // Account validity

    public String validateAccount(BankAccount bankAccount) {

        // Validate account type
        if (!VALID_ACCOUNT_TYPES.contains(bankAccount.getAccountType())) {
            return "Invalid account type. Allowed types: " + VALID_ACCOUNT_TYPES;
        }

        // Check if NIC already has same type account
        boolean exists = bankAccountRepository
                .findByNicNumberAndAccountType(bankAccount.getNicNumber(), bankAccount.getAccountType())
                .isPresent();

        if (exists) {
            return "Account already exists for NIC: " + bankAccount.getNicNumber() +
                    " with type: " + bankAccount.getAccountType();
        }

        return null;
    }


    // account exist or not
    public boolean accountExists(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber).isPresent();
    }


    // Create Account

    public BankAccount createBankAccount(BankAccount bankAccount) {
        bankAccount.setAccountBalance(0.0);
        return bankAccountRepository.save(bankAccount);
    }
// get accounts

    public List<BankAccount> getAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<Double> checkBalance(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .map(BankAccount::getAccountBalance);
    }
}
