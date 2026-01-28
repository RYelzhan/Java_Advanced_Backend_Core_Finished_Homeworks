package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.exception.SubscriptionNotFoundException;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ServiceImpl implements Service {
    private final Map<String, Subscription> bankCards = new HashMap<>();
    private final Map<User, String> bankNumbers = new HashMap<>();

    @Override
    public void subscribe(BankCard bankCard) {
        var subscription = new Subscription(bankCard.getNumber(), LocalDate.now());
        bankCards.put(bankCard.getNumber(), subscription);
        bankNumbers.put(bankCard.getUser(), bankCard.getNumber());
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.ofNullable(bankCards.get(cardNumber));
    }

    @Override
    public List<User> getAllUsers() {
        return bankNumbers.keySet().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return getAllUsers().stream()
                .map(user -> getSubscriptionByBankCardNumber(bankNumbers.get(user))
                        .orElseThrow(() -> new SubscriptionNotFoundException("No subscription for User: " + user)))
                .filter(condition)
                .collect(Collectors.toUnmodifiableList());
    }
}
