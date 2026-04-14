package hh.backend.dryingroombookingsystem.web;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.backend.dryingroombookingsystem.domain.Booking;
import hh.backend.dryingroombookingsystem.domain.BookingRepository;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    private final BookingRepository bookingRepository;

    public BookingRestController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public Iterable<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Booking> getOne(@PathVariable Long id) {
        return bookingRepository.findById(id);
    }
}
