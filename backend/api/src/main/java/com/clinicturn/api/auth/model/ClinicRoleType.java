package com.clinicturn.api.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClinicRoleType {
    ADMIN("ADM", "Administrator"),
    DOCTOR("DOC", "Doctor"),
    RECEPTION("REC", "Receptionist"),
    PATIENT("PAT", "Patient"),
    QUEUE_MANAGER("QMA", "Queue Manager");

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

    @Converter(autoApply = true)
    public static class ClinicRoleTypeConverter implements AttributeConverter<ClinicRoleType, String> {
        @Override
        public String convertToDatabaseColumn(ClinicRoleType attribute) {
            return attribute != null ? attribute.getCode() : null;
        }

        @Override
        public ClinicRoleType convertToEntityAttribute(String dbData) {
            return dbData != null ? fromCode(dbData) : null;
        }
    }
}
