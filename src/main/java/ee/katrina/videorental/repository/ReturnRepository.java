package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.ReturnTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReturnRepository extends JpaRepository<ReturnTransaction, UUID> {
}
