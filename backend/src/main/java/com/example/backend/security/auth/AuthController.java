package com.example.backend.security.auth;

import com.example.backend.config.AppConstants;
import com.example.backend.payload.request.AuthRegistrationRequest;
import com.example.backend.payload.request.AuthLoginRequest;
import com.example.backend.payload.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.Request.AUTH)
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody AuthRegistrationRequest authRegistrationRequest) {
    return new ResponseEntity<>(authService.register(authRegistrationRequest), HttpStatus.OK);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthLoginRequest authLoginRequest) {
    return new ResponseEntity<>(authService.authenticate(authLoginRequest), HttpStatus.OK);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<AuthResponse> refreshToken() {
    return new ResponseEntity<>(authService.refreshToken(), HttpStatus.OK);
  }
}