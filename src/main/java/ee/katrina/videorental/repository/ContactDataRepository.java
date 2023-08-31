package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.ContactData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactDataRepository extends JpaRepository<ContactData, String> {
}
