package hh.backend.dryingroombookingsystem;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import hh.backend.dryingroombookingsystem.domain.Booking;
import hh.backend.dryingroombookingsystem.domain.BookingRepository;
import hh.backend.dryingroombookingsystem.domain.Resident;
import hh.backend.dryingroombookingsystem.domain.ResidentRepository;
import hh.backend.dryingroombookingsystem.domain.Section;
import hh.backend.dryingroombookingsystem.domain.SectionRepository;

@DataJpaTest
public class BookingRepositoryTest {
    private final BookingRepository bookingRepository;
    private final SectionRepository sectionRepository;
    private final ResidentRepository residentRepository;

    public BookingRepositoryTest(
            BookingRepository bookingRepository,
            SectionRepository sectionRepository,
            ResidentRepository residentRepository) {
        this.bookingRepository = bookingRepository;
        this.sectionRepository = sectionRepository;
        this.residentRepository = residentRepository;
    }

    @Test
    void saveBooking() {

        Section section = sectionRepository.findAll().iterator().next();
        Resident resident = residentRepository.findAll().iterator().next();

        Booking booking = new Booking();
        booking.setDate(LocalDate.now());
        booking.setStartTime(LocalTime.of(10, 0));
        booking.setEndTime(LocalTime.of(12, 0));
        booking.setSection(section);
        booking.setResident(resident);

        bookingRepository.save(booking);

        assertThat(bookingRepository.findAll()).isNotEmpty();
    }
}