package com.epam.jmp.app;

import java.util.ServiceLoader;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.bank.Bank;
import com.epam.jmp.service.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        // Load implementations
        Bank bank = ServiceLoader.load(Bank.class).findFirst()
                .orElseThrow(() -> new RuntimeException("No Bank implementation found!"));
        Service service = ServiceLoader.load(Service.class).findFirst()
                .orElseThrow(() -> new RuntimeException("No Service implementation found!"));

        // Create users
        User alice = new User("Alice", "Smith", LocalDate.of(1990, 1, 1));
        User bob = new User("Bob", "Brown", LocalDate.of(2005, 5, 15));
        User charlie = new User("Charlie", "Davis", LocalDate.of(1980, 12, 31));

        // Create bank cards
        BankCard aliceCard = bank.createBankCard(alice, BankCardType.CREDIT);
        BankCard bobCard = bank.createBankCard(bob, BankCardType.DEBIT);
        BankCard charlieCard = bank.createBankCard(charlie, BankCardType.CREDIT);

        // Subscribe users
        service.subscribe(aliceCard);
        service.subscribe(bobCard);
        service.subscribe(charlieCard);

        // Test getAllUsers
        List<User> users = service.getAllUsers();
        System.out.println("All users: " + users);

        // Test getAverageUsersAge
        Optional<Double> avgAge = service.getAverageUsersAge();
        System.out.println("Average users' age: " + avgAge.orElse(-1.0));

        // Test getOldestUser
        Optional<User> oldest = service.getOldestUser();
        System.out.println("Oldest user: " + oldest);

        // Test isPayableUser
        for (User user : users) {
            System.out.println(user + " is payable: " + Service.isPayableUser(user));
        }

        // Test getSubscriptionByBankCardNumber
        Optional<Subscription> aliceSub = service.getSubscriptionByBankCardNumber(aliceCard.getNumber());
        System.out.println("Alice's subscription: " + aliceSub);

        // Test getAllSubscriptionsByCondition (e.g., all subscriptions started this year)
        List<Subscription> recentSubs = service.getAllSubscriptionsByCondition(
                sub -> sub.getStartDate().isAfter(LocalDate.now().minusYears(1))
        );
        System.out.println("Recent subscriptions: " + recentSubs);
    }
}
