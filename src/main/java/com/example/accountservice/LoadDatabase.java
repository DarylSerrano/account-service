package com.example.accountservice;

import com.example.accountservice.account.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDB(AccountRepository repository) {
        return args -> {
            log.info("added " + repository.save(new Account("test", false)));

            log.info("added " + repository.save(new Account("Treasure", true)));
        };
    }
}