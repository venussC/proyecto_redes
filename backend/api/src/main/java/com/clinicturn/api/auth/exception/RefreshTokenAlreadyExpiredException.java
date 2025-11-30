package com.clinicturn.api.auth.exception;

public class RefreshTokenAlreadyExpiredException extends RuntimeException {
    public RefreshTokenAlreadyExpiredException(String message) {
        super(message);
    }
}
