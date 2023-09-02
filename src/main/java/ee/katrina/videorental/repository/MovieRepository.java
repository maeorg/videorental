package ee.katrina.videorental.repository;

import ee.katrina.videorental.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

    List<Movie> findAllByNotRentedOutQuantityIsGreaterThan(Integer n);
}
