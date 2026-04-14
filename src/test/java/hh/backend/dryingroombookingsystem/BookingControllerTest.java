package hh.backend.dryingroombookingsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import hh.backend.dryingroombookingsystem.web.BookingController;
import hh.backend.dryingroombookingsystem.domain.BookingRepository;
import hh.backend.dryingroombookingsystem.domain.SectionRepository;
import hh.backend.dryingroombookingsystem.domain.ResidentRepository;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    private final MockMvc mockMvc;

    @MockitoBean
    private BookingRepository bookingRepository;

    @MockitoBean
    private SectionRepository sectionRepository;

    @MockitoBean
    private ResidentRepository residentRepository;

    public BookingControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void bookingsPageLoads() throws Exception {
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk());
    }
}