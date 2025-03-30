package com.example.antockassignment.config.exception;

public record ErrorResponse(
        ErrorCode errorCode,
        String message,
        Object payload
) {
}