package com.nemal.BankAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionID;
    private LocalDateTime transactionDate;
    private double transactionAmount;
    private String transactionType;

    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    @JsonIgnore
    private BankAccount bankAccount;

    public Transaction() {
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID =" + transactionID +
                ", transactionDateTime =" + transactionDate +
                ", transactionAmount =" + transactionAmount +
                ", transactionType ='" + transactionType + '\'' +
                ", bankAccount =" + bankAccount +
                '}';
    }


}
