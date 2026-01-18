package com.epam.jmp.impl;

import com.epam.jmp.dto.*;
import com.epam.jmp.service.Bank;

import java.util.UUID;

public class BankImpl implements Bank {

    public static final double DEFAULT_CREDIT_LIMIT = 300;

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        return switch (cardType) {
            case CREDIT -> new CreditBankCard(UUID.randomUUID().toString(), user, DEFAULT_CREDIT_LIMIT);
            default ->  new DebitBankCard(UUID.randomUUID().toString(), user, DEFAULT_CREDIT_LIMIT);
        };
    }
}
