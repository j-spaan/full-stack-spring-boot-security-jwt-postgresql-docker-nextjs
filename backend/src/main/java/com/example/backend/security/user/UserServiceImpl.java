package com.example.backend.security.user;

import com.example.backend.payload.exception.ResourceNotFoundException;
import com.example.backend.payload.request.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public record UserServiceImpl(
        UserRepository userRepository,
        UserMapperService userMapperService) implements UserService {

    @Override
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDto> userDtoList = users.stream()
                .map(userMapperService::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtoList);
    }

    @Override
    public ResponseEntity<UserDto> findUserById(UUID id) {
        return ResponseEntity.ok().body(
                userMapperService.convertToDto(this.findById(id))
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user.si.email.not.found",
                        new String[]{email})
                );
    }

    @Override
    public User findUserByEmailOrUsername(String email, String username) {
        return userRepository.findByEmailOrUsername(email, username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user.si.email.username.not.found",
                        new String[]{email})
                );
    }

    @Override
    public User saveUser(User user) {
        if (this.existsUserByEmail(user.getEmail())) {
            throw new ResourceNotFoundException(
                    "user.si.email.exists",
                    new String[]{user.getEmail()}
            );
        }
        return userRepository.save(user);
    }

    @Override
    public void changeUserInfo(User user) {
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(UUID id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user.si.id.not.found",
                        new String[]{id.toString()})
                );
    }

    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}