package com.clinicturn.api.clinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "clinic", name = "schedule",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"clinic_id", "week_day"}, name = "schedule_clinic_id_week_day_unique")
        },
        indexes = {
            @Index(columnList = "clinic_id", name = "fk_schedule_clinic_idx")
        }
)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Schedule's clinic should not be null")
    @JoinColumn(name = "clinic_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_schedule_clinic"))
    private Clinic clinic;

    @NotNull(message = "Schedule's day should not be null")
    @Column(name = "week_day", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private DayOfWeek weekDay;

    @Column(name = "opening")
    private LocalTime opening;

    @Column(name = "closing")
    private LocalTime closing;

    @Builder.Default
    @NotNull(message = "Schedule's isClosed should not be null")
    @Column(name = "is_closed", nullable = false)
    private Boolean isClosed = false;
}
