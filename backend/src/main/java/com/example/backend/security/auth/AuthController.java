package com.example.backend.security.auth;

import com.example.backend.config.AppConstants;
import com.example.backend.payload.request.AuthRegistrationRequest;
import com.example.backend.payload.request.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
  public ResponseEntity<Object> register(@RequestBody AuthRegistrationRequest authRegistrationRequest) {
    return new ResponseEntity<>(authService.register(authRegistrationRequest), HttpStatus.OK);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<Object> authenticate(@RequestBody AuthRequest authRequest) {
    return new ResponseEntity<>(authService.authenticate(authRequest), HttpStatus.OK);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<Object> refreshToken() {
    return new ResponseEntity<>(authService.refreshToken(), HttpStatus.OK);
  }
}