package com.clinicturn.api.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClinicRoleType {
    ADMIN("ADM", "Administrator"),
    DOCTOR("DOC", "Doctor"),
    RECEPTION("REC", "Receptionist"),
    PATIENT("PAT", "Patient");

    private final String code;
    private final String description;

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static ClinicRoleType fromCode(String code) {
        for (ClinicRoleType crt: values()) {
            if (crt.code.equalsIgnoreCase(code)) {
                return crt;
            }
        }
        throw new IllegalArgumentException("Invalid Clinic Role Type Code: " + code);
    }
}
