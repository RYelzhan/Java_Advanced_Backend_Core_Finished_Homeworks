package com.epam.jmp.factory;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.User;

@FunctionalInterface
public interface BankCardFactory {
    BankCard create(String number, User user, double limit);
}
