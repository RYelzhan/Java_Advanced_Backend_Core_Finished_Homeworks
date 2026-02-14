package com.epam.jmp.rest_api_homework.controller;

import com.epam.jmp.rest_api_homework.entity.User;
import com.epam.jmp.rest_api_homework.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{version}/users")
public class UserControllerV1 {

    private final UserService userService;

    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(version = "1.0")
    public ResponseEntity<User> createUserV1(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping(version = "2.0")
    public ResponseEntity<User> createUserV2(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // Version 1: Returns List<User>
    @GetMapping(version = "1.0")
    public ResponseEntity<List<User>> getAllUsersV1() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Version 2: Returns Page<User> (pagination)
    @GetMapping(version = "2.0")
    public ResponseEntity<Page<User>> getAllUsersV2(@PageableDefault(size = 10) Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping(path = "/{id}", version = "1.0")
    public ResponseEntity<User> updateUserV1(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}", version = "2.0")
    public ResponseEntity<User> updateUserV2(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}", version = "1.0")
    public ResponseEntity<Void> deleteUserV1(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}", version = "2.0")
    public ResponseEntity<Void> deleteUserV2(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}