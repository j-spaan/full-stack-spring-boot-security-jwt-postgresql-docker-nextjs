package com.example.backend.security.user;

import com.example.backend.config.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(AppConstants.Request.USERS)
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public void changeUserInfo(@Valid @RequestBody User user) {
        userService.changeUserInfo(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        return userService.deleteUserById(id);
    }
}
