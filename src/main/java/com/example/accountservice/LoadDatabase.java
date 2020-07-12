package com.example.accountservice;

import com.example.accountservice.account.*;
import com.example.accountservice.currency.Currency;
import com.example.accountservice.currency.CurrencyRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDB(AccountRepository accountRepository, CurrencyRepository currencyRepository) {
        return args -> {
            log.info("added " + accountRepository.save(new Account("test", false)));

            log.info("added " + accountRepository.save(new Account("Treasure", true)));

            Currency cur = new Currency("Euros", "EUR");
            log.info("added " + currencyRepository.save(cur));
            log.info("added " + currencyRepository.save(new Currency("US Dollar", "USD")));
            log.info("added " + currencyRepository.save(new Currency("Japanese Yen", "JPY")));

            log.info("added " + accountRepository.save(new Account("With currency", false, 100.0, cur)));

        };
    }
}