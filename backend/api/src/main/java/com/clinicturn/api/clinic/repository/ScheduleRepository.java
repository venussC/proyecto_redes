package com.clinicturn.api.clinic.repository;

import com.clinicturn.api.clinic.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
    SELECT s FROM Schedule s
    WHERE s.clinic.id = :clinicId
    ORDER BY 
        CASE s.weekDay
            WHEN 'MONDAY' THEN 1
            WHEN 'TUESDAY' THEN 2
            WHEN 'WEDNESDAY' THEN 3
            WHEN 'THURSDAY' THEN 4
            WHEN 'FRIDAY' THEN 5
            WHEN 'SATURDAY' THEN 6
            WHEN 'SUNDAY' THEN 7
        END
    """)
    List<Schedule> findOrderedByClinic(Long clinicId);

    List<Schedule> findByClinicIdOrderByWeekDayAsc(Long clinicId);

    boolean existsByClinicIdAndWeekDay(Long clinicId, DayOfWeek weekDay);
}
