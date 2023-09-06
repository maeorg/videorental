package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DirectorRepository extends JpaRepository<Director, UUID> {

    Director findDirectorByFirstNameAndLastNameAndBirthYear(String firstName, String lastName, Integer birthYear);
}
