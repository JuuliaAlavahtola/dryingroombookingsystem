package hh.backend.dryingroombookingsystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hh.backend.dryingroombookingsystem.domain.Apartment;
import hh.backend.dryingroombookingsystem.domain.ApartmentRepository;
import hh.backend.dryingroombookingsystem.domain.AppUser;
import hh.backend.dryingroombookingsystem.domain.UserRepository;
import hh.backend.dryingroombookingsystem.domain.Booking;
import hh.backend.dryingroombookingsystem.domain.BookingRepository;
import hh.backend.dryingroombookingsystem.domain.DryingRoom;
import hh.backend.dryingroombookingsystem.domain.DryingRoomRepository;
import hh.backend.dryingroombookingsystem.domain.Resident;
import hh.backend.dryingroombookingsystem.domain.ResidentRepository;
import hh.backend.dryingroombookingsystem.domain.Section;
import hh.backend.dryingroombookingsystem.domain.SectionRepository;

@SpringBootApplication
public class DryingroombookingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DryingroombookingsystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner createDemo(
			BookingRepository bookingRepository,
			UserRepository userRepository,
			DryingRoomRepository dryingRoomRepository,
			SectionRepository sectionRepository,
			ResidentRepository residentRepository,
			ApartmentRepository apartmentRepository,
			BCryptPasswordEncoder encoder) {

		return (args) -> {

			AppUser user = userRepository.findByUsername("user");
			if (user == null) {
				user = new AppUser("user", encoder.encode("password"), "USER");
				userRepository.save(user);
			}

			AppUser admin = userRepository.findByUsername("admin");
			if (admin == null) {
				admin = new AppUser("admin", encoder.encode("admin"), "ADMIN");
				userRepository.save(admin);
			}

			if (apartmentRepository.count() == 0) {
				for (int i = 1; i <= 90; i++) {
					apartmentRepository.save(new Apartment("A", i));
					apartmentRepository.save(new Apartment("B", i));
				}
			}

			if (residentRepository.count() == 0) {

				Apartment a12 = apartmentRepository.findByBuildingAndNumber("A", 12);
				Apartment b49 = apartmentRepository.findByBuildingAndNumber("B", 49);

				Resident r1 = new Resident(null, "Matti", "Meikäläinen", "m.meikalainen@gmail.com", b49);
				r1.setApartment(a12);

				Resident r2 = new Resident(null, "Pekka", "Pouta", "pekka.pouta@gmail.com", b49);
				r2.setApartment(b49);

				residentRepository.saveAll(List.of(r1, r2));
			}

			if (dryingRoomRepository.count() == 0) {
				String[] buildings = { "A", "B" };
				int[] floors = { 4, 9, 13 };

				for (String b : buildings) {
					for (int f : floors) {
						DryingRoom room = dryingRoomRepository.save(new DryingRoom(b, f));
						for (int i = 1; i <= 3; i++) {
							sectionRepository.save(new Section(i, room));
						}
					}
				}
			}

			if (bookingRepository.count() == 0) {
				List<Section> sections = new ArrayList<>();
				sectionRepository.findAll().forEach(sections::add);

				List<Resident> residents = new ArrayList<>();
				residentRepository.findAll().forEach(residents::add);

				if (sections.size() >= 2 && residents.size() >= 2) {
					Booking b1 = new Booking();
					b1.setDate(LocalDate.now().plusDays(1));
					b1.setStartTime(LocalTime.of(10, 0));
					b1.setEndTime(LocalTime.of(12, 0));
					b1.setSection(sections.get(0));
					b1.setResident(residents.get(0));
					bookingRepository.save(b1);

					Booking b2 = new Booking();
					b2.setDate(LocalDate.now().plusDays(2));
					b2.setStartTime(LocalTime.of(14, 0));
					b2.setEndTime(LocalTime.of(16, 0));
					b2.setSection(sections.get(4));
					b2.setResident(residents.get(1));
					bookingRepository.save(b2);
				}
			}

			System.out.println("Testidata luotu!");
		};
	}
}
