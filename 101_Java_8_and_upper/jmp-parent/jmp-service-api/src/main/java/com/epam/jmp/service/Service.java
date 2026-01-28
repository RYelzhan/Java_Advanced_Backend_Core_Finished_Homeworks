package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Predicate;

public interface Service {

    int LEGAL_ADULT_AGE = 18;

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    default Optional<Double> getAverageUsersAge() {
        List<User> users = getAllUsers();
        LocalDate now = LocalDate.now();

        OptionalDouble avg = users.stream()
                .mapToDouble(user -> ChronoUnit.YEARS.between(user.getBirthday(), now))
                .average();

        return avg.isPresent() ? Optional.of(avg.getAsDouble()) : Optional.empty();
    }

    default Optional<User> getOldestUser() {
        return getAllUsers().stream()
                .min(Comparator.comparing(User::getBirthday)); // Oldest = earliest birthday
    }

    static boolean isPayableUser(User user) {
        LocalDate now = LocalDate.now();

        long age = ChronoUnit.YEARS.between(user.getBirthday(), now);

        return age >= LEGAL_ADULT_AGE;
    }

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);
}
