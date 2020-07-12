package com.example.accountservice.account;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(long id){
        super("Account with id: " + id + " not found");
    }

    public AccountNotFoundException(){
        super("Account doesn't exists");
    }
}