package com.clinicturn.api.security.exception;

public class ClinicUserLockedException extends RuntimeException {

    public ClinicUserLockedException(String message) {
        super(message);
    }
}
