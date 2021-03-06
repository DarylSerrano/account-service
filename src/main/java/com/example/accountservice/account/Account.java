package com.example.accountservice.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Account {
    private @Id @GeneratedValue Long id;
    private String name;
    private String currency;
    private double balance;

    @Column(updatable = false)
    private boolean treasury;

    Account() {
        this.currency = "EUR";
    }

    public Account(String name, Boolean treasury) {
        this.name = name;
        this.treasury = treasury;
        this.currency = "EUR";
    }

    public Account(String name, Boolean treasury, double balance) {
        this.name = name;
        this.treasury = treasury;
        this.currency = "EUR";
        this.balance = balance;
    }

    public Account(String name, Boolean treasury, double balance, String currency) {
        this.name = name;
        this.treasury = treasury;
        this.currency = currency;
        this.balance = balance;
    }
}