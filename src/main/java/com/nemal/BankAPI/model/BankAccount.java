package com.nemal.BankAPI.model;

import jakarta.persistence.*;
import java.util.List;
import java.time.Year;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_gen")
    @SequenceGenerator(
            name = "account_seq_gen",
            sequenceName = "account_seq",  // will create/use this sequence in DB
            allocationSize = 1
    )
    private Long id; // internal PK, not exposed

    @Column(unique = true, nullable = false, length = 12)
    private String accountNumber;

    private String nicNumber;
    private String accountHolderName;
    private double accountBalance;
    private String accountType;

    @PrePersist
    public void generateAccountNumber() {
        if (this.accountNumber == null) {
            String yy = String.valueOf(Year.now().getValue()).substring(2);
            this.accountNumber = yy + String.format("%010d", id);
        }
    }

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    public BankAccount() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", nicNumber='" + nicNumber + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", accountBalance=" + accountBalance +
                ", accountType='" + accountType + '\'' +
                ", transactions=" + transactions +
                '}';
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }
}
