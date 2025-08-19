package com.nemal.BankAPI.service;

public class ApiResponse {
    private String message;
    private Double balance;

    public ApiResponse(String message, Double balance) {
        this.message = message;
        this.balance = balance;
    }



    public String getMessage() {
        return message;
    }

    public Double getBalance() {
        return balance;
    }
}