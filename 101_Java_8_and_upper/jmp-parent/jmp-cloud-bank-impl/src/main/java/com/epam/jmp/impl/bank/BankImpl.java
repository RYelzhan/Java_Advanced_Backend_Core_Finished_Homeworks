package com.epam.jmp.impl.bank;

import com.epam.jmp.dto.*;
import com.epam.jmp.factory.BankCardFactory;
import com.epam.jmp.service.bank.Bank;

import java.util.UUID;

public class BankImpl implements Bank {

    public static final double DEFAULT_CREDIT_LIMIT = 300;

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        BankCardFactory factory = switch (cardType) {
            case CREDIT -> CreditBankCard::new;
            default -> DebitBankCard::new;
        };

        return factory.create(UUID.randomUUID().toString(), user, DEFAULT_CREDIT_LIMIT);
    }
}
