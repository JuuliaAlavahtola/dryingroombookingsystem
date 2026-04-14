package hh.backend.dryingroombookingsystem.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hh.backend.dryingroombookingsystem.domain.Booking;
import hh.backend.dryingroombookingsystem.domain.BookingRepository;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    private final BookingRepository bookingRepository;

    public BookingRestController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // GET all bookings
    @GetMapping
    public List<Booking> getAll() {
        return (List<Booking>) bookingRepository.findAll();
    }

    // GET one booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getOne(@PathVariable Long id) {
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create new booking
    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.ok(saved);
    }

    // PUT update booking
    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        return bookingRepository.findById(id)
                .map(existing -> {
                    updatedBooking.setId(id);
                    Booking saved = bookingRepository.save(updatedBooking);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!bookingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
