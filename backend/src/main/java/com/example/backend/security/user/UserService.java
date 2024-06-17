package com.example.backend.security.user;

import com.example.backend.payload.request.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ResponseEntity<List<UserDto>> findAllUsers();
    ResponseEntity<UserDto> findUserById(UUID id);
    User findUserByEmail(String email);
    User findUserByEmailOrUsername(String email, String username);
    User saveUser(User user);
    void changeUserInfo(User user);
    ResponseEntity<Void> deleteUserById(UUID id);
}