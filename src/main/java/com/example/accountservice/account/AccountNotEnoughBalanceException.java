package com.example.accountservice.account;

public class AccountNotEnoughBalanceException extends RuntimeException {
    AccountNotEnoughBalanceException(Long id){
        super("Account id: " + id + "does not have enough balance");
    }
}