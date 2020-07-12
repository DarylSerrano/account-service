package com.example.accountservice.account;

import lombok.Data;

@Data
public class AccountCreateCriteria {
    private String name;

    private Long currency;

    private double balance;

    private boolean treasury;
}