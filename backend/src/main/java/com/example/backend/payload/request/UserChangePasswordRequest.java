package com.example.backend.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserChangePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirmation;
}