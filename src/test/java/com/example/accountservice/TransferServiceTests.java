package com.example.accountservice;

import java.util.Optional;

import com.example.accountservice.account.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTests {
    TransferService transferService;

    @Mock
    AccountRepository repository;

    @BeforeEach
    public void init() {
        transferService = new TransferService(repository);
    }

    @Test
    public void shouldProperlySetBalance() {
        Account from = new Account("AccountFrom", false, 1.0);
        from.setId((long) 1);
        Account to = new Account("AccounTo", false, 0.0);
        to.setId((long) 2);

        Mockito.when(repository.findById(from.getId())).thenReturn(Optional.of(from));
        Mockito.when(repository.findById(to.getId())).thenReturn(Optional.of(to));

        Mockito.when(repository.save(from)).thenReturn(from);
        Mockito.when(repository.save(to)).thenReturn(to);

        transferService.transferToAccount(from.getId(), to.getId(), 0.5);

        assertEquals(to.getBalance(), 0.5, 0.001);
    }

    @Test
    public void shouldBlockTransferOnNegativeBalanceAndNotTreasury() {
        Account from = new Account("AccountFrom", false, 1.0);
        from.setId((long) 1);
        Account to = new Account("AccounTo", false, 0.0);
        to.setId((long) 2);

        Mockito.when(repository.findById(from.getId())).thenReturn(Optional.of(from));
        Mockito.when(repository.findById(to.getId())).thenReturn(Optional.of(to));

        assertThrows(AccountNotEnoughBalanceException.class, () -> {
            transferService.transferToAccount(from.getId(), to.getId(), 1.5);
        });
    }

    @Test
    public void shouldAllowTransferOnNegativeBalanceAndTreasury() {
        Account from = new Account("AccountFrom", true, 1.0);
        from.setId((long) 1);
        Account to = new Account("AccounTo", false, 0.0);
        to.setId((long) 2);

        Mockito.when(repository.findById(from.getId())).thenReturn(Optional.of(from));
        Mockito.when(repository.findById(to.getId())).thenReturn(Optional.of(to));

        Mockito.when(repository.save(from)).thenReturn(from);
        Mockito.when(repository.save(to)).thenReturn(to);

        transferService.transferToAccount(from.getId(), to.getId(), 1.5);

        assertEquals(to.getBalance(), 1.5, 0.001);
        assertEquals(from.getBalance(), -0.5, 0.001);
    }
}