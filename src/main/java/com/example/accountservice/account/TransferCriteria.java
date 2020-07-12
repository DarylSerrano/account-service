package com.example.accountservice.account;

import lombok.Data;

@Data
public class TransferCriteria {
    private Long idTo;
    private double ammountToTransfer;
}