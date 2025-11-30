package com.clinicturn.api.clinic.exception;

public class UnavailableRoomException extends RuntimeException {
    public UnavailableRoomException(String message) {
        super(message);
    }
}
