package com.example.accountservice.currency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Currency {
    private @Id @GeneratedValue Long id;
    private String name;
    private String ISO;

    public Currency() {

    }

    public Currency(String name, String ISO) {
        this.name = name;
        this.ISO = ISO;
    }
}