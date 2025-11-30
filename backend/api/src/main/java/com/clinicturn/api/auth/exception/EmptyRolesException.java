package com.clinicturn.api.auth.exception;

public class EmptyRolesException extends RuntimeException {
    public EmptyRolesException(String message) {
        super(message);
    }
}
