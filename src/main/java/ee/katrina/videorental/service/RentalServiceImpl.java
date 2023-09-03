package ee.katrina.videorental.service;

import ee.katrina.videorental.entity.RentalTransaction;
import ee.katrina.videorental.entity.RentalTransactionLine;
import ee.katrina.videorental.model.MovieType;
import ee.katrina.videorental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${movie.price.premium}")
    Integer PREMIUM_PRICE;

    @Value("${movie.price.basic}")
    Integer BASIC_PRICE;

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
        RentalTransaction pricedTransaction = calculateRentalPrice(rentalTransaction);
        movieService.decreaseQuantity(rentalTransaction);
        return rentalRepository.save(pricedTransaction);
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

//    New releases – Price is PREMIUM_PRICE times number of days rented.
//    Regular films – Price is BASIC_PRICE for the first 3 days and then BASIC_PRICE times the number of days over 3
//    Old film - Price is BASIC_PRICE for the first 5 days and then BASIC_PRICE times the number of days over 5
    public RentalTransaction calculateRentalPrice(RentalTransaction rentalTransaction) {
        double totalSum = 0;
        for (RentalTransactionLine transactionLine : rentalTransaction.getRentalTransactionLines()) {
            double price = 0;
            MovieType type = transactionLine.getMovie().getMovieType();
            Integer quantity = transactionLine.getQuantity();
            Integer daysRented = transactionLine.getDaysRented();
            switch (type) {
                case NEW:
                    price = PREMIUM_PRICE * daysRented;
                    break;
                case REGULAR:
                    if (daysRented <= 3) {
                        price = BASIC_PRICE;
                    } else {
                        price = BASIC_PRICE + ((daysRented-3) * BASIC_PRICE);
                    }
                    break;
                case OLD:
                    if (daysRented <= 5) {
                        price = BASIC_PRICE;
                    } else {
                        price = BASIC_PRICE + ((daysRented-5) * BASIC_PRICE);
                    }
                    break;
                default:
                    throw new RuntimeException("Wrong movie type");
            }
            price  *= quantity;
            transactionLine.setPrice(price);
            totalSum += price;
        }
        rentalTransaction.setTotalSum(totalSum);
        return rentalTransaction;
    }

}
