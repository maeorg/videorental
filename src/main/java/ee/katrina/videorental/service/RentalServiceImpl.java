package ee.katrina.videorental.service;

import ee.katrina.videorental.entity.RentalTransaction;
import ee.katrina.videorental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    MovieService movieService;

    @Override
    public List<RentalTransaction> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public Optional<RentalTransaction> getRentalById(UUID rentalId) {
        return rentalRepository.findById(rentalId);
    }

    @Override
    public RentalTransaction addRental(RentalTransaction rentalTransaction) {
        movieService.decreaseQuantity(rentalTransaction);
        return rentalRepository.save(rentalTransaction);
    }

    @Override
    public boolean deleteRentalById(UUID rentalId) {
        if (rentalRepository.existsById(rentalId)) {
            rentalRepository.deleteById(rentalId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<RentalTransaction> updateRentalById(UUID rentalId, RentalTransaction rentalTransaction) {
        if (rentalRepository.findById(rentalId).isEmpty()) {
            return Optional.empty();
        }
        Optional<RentalTransaction> foundRental = rentalRepository.findById(rentalId);
        rentalTransaction.setId(foundRental.get().getId());
        return Optional.ofNullable(rentalRepository.save(rentalTransaction));
    }

}
