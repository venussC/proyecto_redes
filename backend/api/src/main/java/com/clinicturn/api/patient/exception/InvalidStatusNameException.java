package com.clinicturn.api.patient.exception;

public class InvalidStatusNameException extends RuntimeException {
    public InvalidStatusNameException(String message) {
        super(message);
    }
}
