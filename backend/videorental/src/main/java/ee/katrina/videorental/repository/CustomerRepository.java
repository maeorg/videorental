package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>  {

    Customer findByUsername(String username);
}
