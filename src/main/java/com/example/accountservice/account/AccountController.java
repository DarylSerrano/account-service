package com.example.accountservice.account;

import org.springframework.http.HttpStatus;
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

    private final TransferService transferService;

    public AccountController(AccountRepository repository, TransferService transferService) {
        this.repository = repository;
        this.transferService = transferService;
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    public Account getOneAccount(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account newAccount) {
        return new ResponseEntity<>(repository.save(newAccount), HttpStatus.CREATED);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody Account newAccount) {
        Account updatedAccount = repository.findById(id).map(account -> {
            account.setName(newAccount.getName());
            return repository.save(account);
        }).orElseGet(() -> {
            newAccount.setId(id);
            return repository.save(newAccount);
        });

        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        boolean isPresent = repository.findById(id).isPresent();

        repository.deleteById(id);

        return isPresent ? ResponseEntity.accepted().build() : ResponseEntity.noContent().build();
    }

    @PutMapping("/accounts/{id}/transfer")
    public ResponseEntity<?> transferAmmout(@PathVariable Long id, @RequestBody TransferCriteria criteria) {
        transferService.transferToAccount(id, criteria.getIdTo(), criteria.getAmmountToTransfer());

        return ResponseEntity.ok().build();
    }

}