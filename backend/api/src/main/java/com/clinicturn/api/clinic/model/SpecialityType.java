package com.clinicturn.api.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SpecialityType {
    MED_GENERAL("MEDGEN", "Medicina General"),
    DENTISTA("DENTIS", "Dentista"),
    OFTALMOLOGIA("OFTALM", "Oftalmologia"),
    CARDIOLOGIA("CARDIO", "Cardiologia"),
    FARMACIA("FARMAC", "Farmacia"),
    PEDIATRIA("PEDIAT", "Pediatria");

    private final String code;
    private final String displayName;

    public static SpecialityType fromCode(String code) {
        return Arrays.stream(values())
                .filter(t -> t.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown speciality code: " + code));
    }
}
