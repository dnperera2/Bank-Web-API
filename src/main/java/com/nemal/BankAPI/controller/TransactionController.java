package com.nemal.BankAPI.controller;

import com.nemal.BankAPI.dto.ApiResponse;
import com.nemal.BankAPI.dto.TransactionRequest;
import com.nemal.BankAPI.dto.TransactionResponseDTO;
import com.nemal.BankAPI.model.Transaction;
import com.nemal.BankAPI.service.BankAccountService;
import com.nemal.BankAPI.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankAccountService bankAccountService;

    //deposit

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<ApiResponse> deposit(
            @PathVariable String accountNumber,
            @RequestBody TransactionRequest request) { // ApiResponse DTO is used to return the data
        return transactionService.deposit(accountNumber, request)
                .map(acc -> ResponseEntity.ok(new ApiResponse("Deposit successful", acc.getAccountBalance())))
                .orElse(ResponseEntity.badRequest().body(new ApiResponse("Invalid Account Number", null)));
    }

    //withdraw

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<ApiResponse> withdraw(
            @PathVariable String accountNumber,
            @RequestBody TransactionRequest request) { // ApiResponse DTO is used to return the data
        try {
            return transactionService.withdraw(accountNumber, request)
                    .map(acc -> ResponseEntity.ok(new ApiResponse("Withdraw successful", acc.getAccountBalance())))
                    .orElse(ResponseEntity.badRequest().body(new ApiResponse("Invalid Account Number", null)));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse(ex.getMessage(), null));
        }
    }


    //transaction history

    @GetMapping("/{accountNumber}/history")
    public ResponseEntity<?> getTransactionHistory(@PathVariable String accountNumber) {
        if (!bankAccountService.accountExists(accountNumber)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid Account number"));
        }

        List<TransactionResponseDTO> transactions = transactionService.getTransactionHistory(accountNumber);
        if (transactions.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No transactions found for this account"));
        }

        return ResponseEntity.ok(transactions);
    }

}
