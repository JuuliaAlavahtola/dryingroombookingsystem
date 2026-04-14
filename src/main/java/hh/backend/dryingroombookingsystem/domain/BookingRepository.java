package hh.backend.dryingroombookingsystem.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Query("""
                SELECT b FROM Booking b
                WHERE b.section.id = :sectionId
                AND b.date = :date
                AND b.startTime < :endTime
                AND b.endTime > :startTime
            """)
    List<Booking> findOverlapping(
            Long sectionId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime);

    List<Booking> findByResidentId(Long residentId);
}
