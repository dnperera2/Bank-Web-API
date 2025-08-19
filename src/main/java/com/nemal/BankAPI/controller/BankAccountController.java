package com.nemal.BankAPI.controller;

import com.nemal.BankAPI.model.BankAccount;
import com.nemal.BankAPI.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAccounts() {

        return ResponseEntity.ok(bankAccountService.getAccounts());
    }

    @PostMapping("/create") // ? mean we can get either error messege or account
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccount bankAccount) {
        String error = bankAccountService.validateAccount(bankAccount);
        if (error != null) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", error)
            );
        }

        // save account
        BankAccount createdAccount = bankAccountService.createBankAccount(bankAccount);
        return ResponseEntity.ok(
                Map.of(
                        "account-number", createdAccount.getAccountNumber()
                )
        );

    }

}
