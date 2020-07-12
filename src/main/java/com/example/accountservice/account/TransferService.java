package com.example.accountservice.account;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final AccountRepository repository;

    public TransferService(AccountRepository repository) {
        this.repository = repository;
    }

    public void transferToAccount(Long idFrom, Long idTo, double ammountToTransfer) {
        try {
            Account from = repository.findById(idFrom).get();
            Account to = repository.findById(idTo).get();

            if (!from.isTreasury() && from.getBalance() < ammountToTransfer) {
                throw new AccountNotEnoughBalanceException(idFrom);
            }

            from.setBalance(from.getBalance() - ammountToTransfer);
            to.setBalance(to.getBalance() + ammountToTransfer);

            repository.save(from);
            repository.save(to);

        } catch (NoSuchElementException e) {
            throw new AccountNotFoundException();
        }
    }
}