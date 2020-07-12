package com.example.accountservice.account;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public Account getOneAccount(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account newAccount) {
        return repository.save(newAccount);
    }

    @PutMapping("/accounts/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account newAccount) {
        Account updatedAccount = repository.findById(id).map(account -> {
            account.setName(newAccount.getName());
            return repository.save(account);
        }).orElseGet(() -> {
            newAccount.setId(id);
            return repository.save(newAccount);
        });

        return updatedAccount;
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}