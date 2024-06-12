package com.example.backend.security.user;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAllUsers();
    ResponseEntity<User> findUserById(UUID id);
    User findUserByEmailOrUsername(String email, String username);
    User saveUser(User user);
    void changeUserInfo(User user);
    void deleteUserById(UUID id);
}