package com.example.backend.payload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidBearerTokenException extends RuntimeException {

    private final String message;
    private final String[] args;
}