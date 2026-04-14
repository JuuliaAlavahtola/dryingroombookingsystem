package hh.backend.dryingroombookingsystem.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import hh.backend.dryingroombookingsystem.domain.Booking;
import hh.backend.dryingroombookingsystem.domain.BookingRepository;
import hh.backend.dryingroombookingsystem.domain.ResidentRepository;
import hh.backend.dryingroombookingsystem.domain.SectionRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {

    private final BookingRepository bookingRepository;
    private final SectionRepository sectionRepository;
    private final ResidentRepository residentRepository;

    public BookingController(BookingRepository bookingRepository,
            SectionRepository sectionRepository,
            ResidentRepository residentRepository) {
        this.bookingRepository = bookingRepository;
        this.sectionRepository = sectionRepository;
        this.residentRepository = residentRepository;
    }

    @GetMapping("/bookings")
    public String getBookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "bookings";
    }

    @GetMapping("/add")
    public String addBooking(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("residents", residentRepository.findAll());
        return "addbooking";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editBooking(@PathVariable Long id, Model model) {
        bookingRepository.findById(id).ifPresent(b -> model.addAttribute("booking", b));
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("residents", residentRepository.findAll());
        return "editbooking";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Booking booking, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("sections", sectionRepository.findAll());
            model.addAttribute("residents", residentRepository.findAll());
            return booking.getId() == null ? "addbooking" : "editbooking";
        }
        List<Booking> overlapping = bookingRepository.findOverlapping(
                booking.getSection().getId(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime());

        if (booking.getId() != null) {
            overlapping.removeIf(b -> b.getId().equals(booking.getId()));
        }

        if (!overlapping.isEmpty()) {
            model.addAttribute("error", "Aika on jo varattu");
            model.addAttribute("sections", sectionRepository.findAll());
            model.addAttribute("residents", residentRepository.findAll());
            return booking.getId() == null ? "addbooking" : "editbooking";
        }

        bookingRepository.save(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "redirect:/bookings";
    }
}
