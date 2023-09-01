package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.RentalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalTransactionRepository extends JpaRepository<RentalTransaction, UUID> {
}
