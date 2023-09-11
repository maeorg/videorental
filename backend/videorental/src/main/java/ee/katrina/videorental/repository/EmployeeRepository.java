package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Employee findByUsername(String username);
}
