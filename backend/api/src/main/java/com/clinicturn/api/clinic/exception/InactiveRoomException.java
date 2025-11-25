package com.clinicturn.api.clinic.exception;

public class InactiveRoomException extends RuntimeException {
    public InactiveRoomException(String message) {
        super(message);
    }
}
