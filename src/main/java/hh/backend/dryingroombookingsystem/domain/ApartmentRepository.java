package hh.backend.dryingroombookingsystem.domain;

import org.springframework.data.repository.CrudRepository;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {
    Apartment findByBuildingAndNumber(String building, int number);

}
