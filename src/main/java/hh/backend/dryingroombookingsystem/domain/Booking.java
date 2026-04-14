package hh.backend.dryingroombookingsystem.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Valitse päivämäärä")
    @FutureOrPresent
    private LocalDate date;

    @NotNull(message = "Valitse aloitus aika")
    private LocalTime startTime;

    @NotNull(message = "Valitse päättymis aika")
    private LocalTime endTime;

    @NotNull(message = "Valitse osio")
    @ManyToOne
    @JsonIgnoreProperties
    @JoinColumn(name = "section_id")
    private Section section;

    @NotNull(message = "Valitse asukas")
    @ManyToOne
    @JsonIgnoreProperties({ "dryingRoom" })
    @JoinColumn(name = "resident_id")
    private Resident resident;

    public Booking() {
    }

    public Booking(LocalDate date, LocalTime startTime, LocalTime endTime, Section section,
            Resident resident) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.section = section;
        this.resident = resident;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    @Override
    public String toString() {
        return "Booking [id=" + id + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
                + ", section=" + section + ", resident=" + resident + "]";
    }

}
