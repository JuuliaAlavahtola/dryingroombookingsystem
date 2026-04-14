package hh.backend.dryingroombookingsystem.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ResidentRepository extends CrudRepository<Resident, Long> {
    Resident findByEmail(String email);

    List<Resident> findAllByLastName(String lastName);

}
