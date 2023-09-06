package ee.katrina.videorental.service;

import ee.katrina.videorental.entity.RentalTransaction;
import ee.katrina.videorental.entity.RentalTransactionLine;
import ee.katrina.videorental.entity.ReturnTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentalService {
    List<RentalTransaction> getAllRentals();

    Optional<RentalTransaction> getRentalById(UUID rentalId);

    RentalTransaction addRental(RentalTransaction rentalTransaction);

    boolean deleteRentalById(UUID rentalId);

    Optional<RentalTransaction> updateRentalById(UUID rentalId, RentalTransaction rentalTransaction);

    RentalTransaction calculateRentalPrice(RentalTransaction rentalTransaction);

    RentalTransactionLine getRentalTransactionLineByMovieId(UUID movieId);

    double returnMovieAndCalculateLateFees(UUID movieId);

    List<ReturnTransaction> getAllReturns();

    Optional<ReturnTransaction> getReturnTransactionById(UUID returnId);
}
