package hh.backend.dryingroombookingsystem.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    @ManyToOne
    @JsonIgnoreProperties
    @JoinColumn(name = "drying_room_id")
    private DryingRoom dryingRoom;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "section")
    private List<Booking> bookings;

    public Section() {
    }

    public Section(int number, DryingRoom dryingRoom) {
        this.number = number;
        this.dryingRoom = dryingRoom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public DryingRoom getDryingRoom() {
        return dryingRoom;
    }

    public void setDryingRoom(DryingRoom dryingRoom) {
        this.dryingRoom = dryingRoom;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
