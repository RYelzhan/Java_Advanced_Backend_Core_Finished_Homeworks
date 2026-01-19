package com.epam.jmp.app;

import com.epam.jmp.dto.*;
import com.epam.jmp.service.Service;
import com.epam.jmp.service.bank.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private Service service;
    private User alice;
    private User bob;
    private User charlie;
    private BankCard aliceCard;

    @BeforeEach
    void setUp() {
        // Load implementations
        Bank bank = ServiceLoader.load(Bank.class).findFirst()
                .orElseThrow(() -> new RuntimeException("No Bank implementation found!"));
        service = ServiceLoader.load(Service.class).findFirst()
                .orElseThrow(() -> new RuntimeException("No Service implementation found!"));

        // Create users
        alice = new User("Alice", "Smith", LocalDate.of(1990, 1, 1));
        bob = new User("Bob", "Brown", LocalDate.of(2005, 5, 15));
        charlie = new User("Charlie", "Davis", LocalDate.of(1980, 12, 31));

        // Create bank cards
        aliceCard = bank.createBankCard(alice, BankCardType.CREDIT);
        BankCard bobCard = bank.createBankCard(bob, BankCardType.DEBIT);
        BankCard charlieCard = bank.createBankCard(charlie, BankCardType.CREDIT);

        // Subscribe users
        service.subscribe(aliceCard);
        service.subscribe(bobCard);
        service.subscribe(charlieCard);
    }

    @Test
    void testAllUsers() {
        List<User> users = service.getAllUsers();
        assertEquals(3, users.size());
        assertTrue(users.contains(alice));
        assertTrue(users.contains(bob));
        assertTrue(users.contains(charlie));
    }

    @Test
    void testAverageUsersAge() {
        Optional<Double> avgAge = service.getAverageUsersAge();
        assertTrue(avgAge.isPresent());
        // The expected value is 33.666... for my test data and current date of 19.01.2026
        assertTrue(avgAge.get() > 30 && avgAge.get() < 40);
    }

    @Test
    void testOldestUser() {
        Optional<User> oldest = service.getOldestUser();
        assertTrue(oldest.isPresent());
        assertEquals(charlie, oldest.get());
    }

    @Test
    void testIsPayableUser() {
        assertTrue(Service.isPayableUser(alice));
        assertTrue(Service.isPayableUser(bob));
        assertTrue(Service.isPayableUser(charlie));
    }

    @Test
    void testGetSubscriptionByBankCardNumber() {
        Optional<Subscription> aliceSub = service.getSubscriptionByBankCardNumber(aliceCard.getNumber());
        assertTrue(aliceSub.isPresent());
        assertEquals(aliceCard.getNumber(), aliceSub.get().getBankcard());
    }

    @Test
    void testGetAllSubscriptionsByCondition() {
        List<Subscription> recentSubs = service.getAllSubscriptionsByCondition(
                sub -> sub.getStartDate().isAfter(LocalDate.now().minusYears(1))
        );
        assertEquals(3, recentSubs.size());
    }
}