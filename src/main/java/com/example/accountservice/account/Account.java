package com.example.accountservice.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.accountservice.currency.Currency;

import lombok.Data;

@Entity
@Data
public class Account {
    private @Id @GeneratedValue Long id;
    private String name;

    @ManyToOne
    private Currency currency;

    private double balance;

    @Column(updatable = false)
    private boolean treasury;

    Account() {
    }

    public Account(String name, Boolean treasury) {
        this.name = name;
        this.treasury = treasury;
    }

    public Account(String name, Boolean treasury, double balance) {
        this.name = name;
        this.treasury = treasury;
        this.balance = balance;
    }

    public Account(String name, Boolean treasury, double balance, Currency currency) {
        this.name = name;
        this.treasury = treasury;
        this.balance = balance;
        this.currency = currency;
    }
}