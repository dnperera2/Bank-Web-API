package com.nemal.BankAPI.controller;

import com.nemal.BankAPI.service.ApiResponse;
import com.nemal.BankAPI.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<ApiResponse> deposit(
            @PathVariable String accountNumber,
            @RequestBody double amount) { // ApiResponse DTO is used to return the data
        return transactionService.deposit(accountNumber, amount)
                .map(acc -> ResponseEntity.ok(new ApiResponse("Deposit successful", acc.getAccountBalance())))
                .orElse(ResponseEntity.badRequest().body(new ApiResponse("Invalid Account Number", null)));
    }


    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<ApiResponse> withdraw(
            @PathVariable String accountNumber,
            @RequestBody double amount) { // ApiResponse DTO is used to return the data
        try {
            return transactionService.withdraw(accountNumber, amount)
                    .map(acc -> ResponseEntity.ok(new ApiResponse("Withdraw successful", acc.getAccountBalance())))
                    .orElse(ResponseEntity.badRequest().body(new ApiResponse("Invalid Account Number", null)));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse(ex.getMessage(), null));
        }
    }



}
