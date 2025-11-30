package com.clinicturn.api.security.exception;

public class ClinicUserDisabledException extends RuntimeException {

    public ClinicUserDisabledException(String message) {
        super(message);
    }
}
