package com.epam.jmp.rest_api_homework.repository;

// UserRepository.java

import com.epam.jmp.rest_api_homework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
