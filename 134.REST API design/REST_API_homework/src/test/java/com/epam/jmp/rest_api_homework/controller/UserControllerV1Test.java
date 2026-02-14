package com.epam.jmp.rest_api_homework.controller;

import com.epam.jmp.rest_api_homework.entity.User;
import com.epam.jmp.rest_api_homework.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    // Helper method for Basic Auth header
    private String basicAuth(String username, String password) {
        return "Basic " + Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes());
    }

    @Test
    @DisplayName("POST /api/v1/users - createUser")
    void createUser() throws Exception {
        User user = new User();
        user.setUsername("alice");
        user.setEmail("alice@example.com");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", basicAuth("alice", "password")) // Use valid username/password
                        .content("""
                    {
                        "username": "alice",
                        "email": "alice@example.com"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    @DisplayName("GET /api/v1/users - getAllUsersV1")
    void getAllUsersV1() throws Exception {
        List<User> users = Arrays.asList(
                new User("alice", "alice@example.com"),
                new User("bob", "bob@example.com")
        );
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", basicAuth("alice", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("alice"))
                .andExpect(jsonPath("$[1].username").value("bob"));
    }

    @Test
    @DisplayName("GET /api/v2/users - getAllUsersV2 (paginated)")
    void getAllUsersV2() throws Exception {
        List<User> users = List.of(new User("alice", "alice@example.com"));
        Page<User> page = new PageImpl<>(users, PageRequest.of(0, 10), 1);

        when(userService.getAllUsers(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v2/users")
                        .header("Authorization", basicAuth("alice", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].username").value("alice"));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} - updateUserV1")
    void updateUserV1() throws Exception {
        User updated = new User("alice", "alice_new@example.com");
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", basicAuth("alice", "password"))
                        .content("""
                    {
                        "username": "alice",
                        "email": "alice_new@example.com"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("alice_new@example.com"));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} - updateUserV1 not found")
    void updateUserV1NotFound() throws Exception {
        when(userService.updateUser(eq(99L), any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", basicAuth("alice", "password"))
                        .content("""
                    {
                        "username": "ghost",
                        "email": "ghost@example.com"
                    }
                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} - deleteUserV1")
    void deleteUserV1() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/v1/users/1")
                        .header("Authorization", basicAuth("alice", "password")))
                .andExpect(status().isNoContent());
    }
}