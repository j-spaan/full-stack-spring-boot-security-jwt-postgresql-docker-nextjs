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
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public void changeUserInfo(@Valid @RequestBody User user) {
        userService.changeUserInfo(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public void deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
    }
}
