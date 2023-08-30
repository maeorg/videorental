package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.Actor;
import ee.katrina.videorental.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActorRepository extends JpaRepository<Actor, UUID> {

    Actor findActorByFirstNameAndLastNameAndBirthYear(String firstName, String lastName, Integer birthYear);
}
