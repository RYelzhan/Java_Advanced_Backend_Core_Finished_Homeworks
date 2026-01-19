package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ServiceImplTest extends TestCase {

    private ServiceImpl service;
    private User user1;
    private User user2;

    @Override
    protected void setUp() {
        service = new ServiceImpl();
        user1 = new User("Alice", "Smith", LocalDate.of(1990, 1, 1));
        user2 = new User("Bob", "Brown", LocalDate.of(2005, 5, 15));
        BankCard card1 = new BankCard("1111", user1);
        BankCard card2 = new BankCard("2222", user2);

        // Simulate adding users and subscriptions
        service.subscribe(card1);
        service.subscribe(card2);
    }

    public void testSubscribeAndGetSubscriptionByBankCardNumber() {
        Optional<Subscription> subOpt = service.getSubscriptionByBankCardNumber("1111");
        assertTrue(subOpt.isPresent());
        assertEquals("1111", subOpt.get().getBankcard());
    }

    public void testGetAllUsers() {
        List<User> users = service.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    public void testGetAverageUsersAge() {
        Optional<Double> avg = service.getAverageUsersAge();
        assertTrue(avg.isPresent());
        assertTrue(avg.get() > 0);
    }

    public void testGetOldestUser() {
        Optional<User> oldest = service.getOldestUser();
        assertTrue(oldest.isPresent());
        assertEquals(user1, oldest.get());
    }

    public void testGetAllSubscriptionsByCondition() {
        List<Subscription> subs = service.getAllSubscriptionsByCondition(
                sub -> sub.getBankcard().equals("1111")
        );
        assertEquals(1, subs.size());
        assertEquals("1111", subs.getFirst().getBankcard());
    }
}
