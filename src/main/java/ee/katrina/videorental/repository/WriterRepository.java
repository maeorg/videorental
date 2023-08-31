package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WriterRepository extends JpaRepository<Writer, String> {

    Writer findWriterByFirstNameAndLastNameAndBirthYear(String firstName, String lastName, Integer birthYear);
}
