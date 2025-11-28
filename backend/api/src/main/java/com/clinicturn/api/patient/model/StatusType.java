package com.clinicturn.api.patient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StatusType {
    WAITING,
    CALLED,
    SEEN,
    COMPLETED,
    CANCELLED;

    public static StatusType fromName(String name) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + name));
    }
}
