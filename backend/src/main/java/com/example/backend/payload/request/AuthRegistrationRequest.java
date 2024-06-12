package com.example.backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegistrationRequest {

  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private String password;
}