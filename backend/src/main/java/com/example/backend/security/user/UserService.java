package com.example.backend.security.user;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    ResponseEntity<User> getUserById(UUID id);
    void changeUserInfo(User user);
    void deleteUserById(UUID id);
}