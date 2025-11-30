package com.clinicturn.api.patient.exception;

public class TurnStillActiveException extends RuntimeException {
    public TurnStillActiveException(String message) {
        super(message);
    }
}
