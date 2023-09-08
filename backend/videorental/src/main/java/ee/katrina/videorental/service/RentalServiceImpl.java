package ee.katrina.videorental.service;

import ee.katrina.videorental.entity.*;
import ee.katrina.videorental.model.MovieType;
import ee.katrina.videorental.repository.CustomerRepository;
import ee.katrina.videorental.repository.MovieRepository;
import ee.katrina.videorental.repository.RentalRepository;
import ee.katrina.videorental.repository.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    ReturnRepository returnRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CustomerRepository customerRepository;

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
        RentalTransaction savedRentalTransaction = rentalRepository.save(pricedTransaction);

        // printout
        for (RentalTransactionLine line : savedRentalTransaction.getRentalTransactionLines()) {
            System.out.println(line.getMovie().getTitle() + " (" + line.getMovie().getMovieType() + ") " +
                    line.getDaysRented() + " days " + (int)line.getPrice() + " EUR");
        }
        System.out.println("Total price: " + savedRentalTransaction.getTotalSum());
        //

        return savedRentalTransaction;
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
        Long bonusPoints = 0L;
        Customer customer = rentalTransaction.getCustomer();
        for (RentalTransactionLine transactionLine : rentalTransaction.getRentalTransactionLines()) {
            if (transactionLine.getMovie().isRentedOut()) {
                throw new RuntimeException("Movie is already rented out");
            } else {
                Movie movie = transactionLine.getMovie();
                movie.setRentedOut(true);
                movieRepository.save(movie);
                transactionLine.setTransactionFinished(false);
            }
            MovieType type = transactionLine.getMovie().getMovieType();
            int daysRented = transactionLine.getDaysRented();
            double price = calculatePriceForTransactionLine(type, daysRented);
            if (type == MovieType.NEW) {
                while (customer.getBonusPoints() >= 25 && daysRented > 0) {
                    price -= PREMIUM_PRICE;
                    customer.setBonusPoints(customer.getBonusPoints() - 25);
                    System.out.println("Paid with 25 bonus points");
                    daysRented -= 1;
                }
                bonusPoints += 2L;
            } else {
                bonusPoints += 1L;
            }
            transactionLine.setPrice(price);
            totalSum += price;
        }

        customer.setBonusPoints(customer.getBonusPoints() + bonusPoints);
        customerRepository.save(customer);

        rentalTransaction.setTotalSum(totalSum);
        return rentalTransaction;
    }

    private double calculatePriceForTransactionLine(MovieType type, Integer daysRented) {
        double price = 0;
        switch (type) {
            case NEW:
                price = PREMIUM_PRICE * daysRented;
                break;
            case REGULAR:
                if (daysRented <= 3) {
                    price = BASIC_PRICE;
                } else {
                    price = BASIC_PRICE + ((daysRented - 3) * BASIC_PRICE);
                }
                break;
            case OLD:
                if (daysRented <= 5) {
                    price = BASIC_PRICE;
                } else {
                    price = BASIC_PRICE + ((daysRented - 5) * BASIC_PRICE);
                }
                break;
            default:
                throw new RuntimeException("Wrong movie type");
        }
        return price;
    }

    @Override
    public RentalTransactionLine getRentalTransactionLineByMovieId(UUID movieId) {
        for (RentalTransaction transaction : rentalRepository.findAll()) {
            for (RentalTransactionLine transactionLine : transaction.getRentalTransactionLines()) {
                if (!transactionLine.isTransactionFinished() && transactionLine.getMovie().getId().equals(movieId)) {
                    transactionLine.getMovie().setRentedOut(false);
                    transactionLine.setTransactionFinished(true);
                    rentalRepository.save(transaction);
                    return transactionLine;
                }
            }
        }
        throw new RuntimeException("Not finished rental transaction line for the movie not found");
    }

    public double returnMovieAndCalculateLateFees(UUID movieId) {
        double lateFee = 0;
        RentalTransactionLine transactionLine = getRentalTransactionLineByMovieId(movieId);
        LocalDateTime dueBack = transactionLine.getCreatedDate().plusDays(transactionLine.getDaysRented());
        if (LocalDateTime.now().isAfter(dueBack)) {
            double originalPrice = transactionLine.getPrice();
            MovieType type = transactionLine.getMovie().getMovieType();
            Integer daysRented = (int) Duration.between(transactionLine.getCreatedDate(), LocalDateTime.now()).toDays() + 1;
            double longerReturnPrice = calculatePriceForTransactionLine(type, daysRented);
            lateFee = longerReturnPrice - originalPrice;
        }
        ReturnTransaction returnTransaction = new ReturnTransaction();
        returnTransaction.setRentalTransactionLine(transactionLine);
        returnTransaction.setMovie(transactionLine.getMovie());
        returnTransaction.setLateFee(lateFee);
        returnRepository.save(returnTransaction);
        return lateFee;
    }

    @Override
    public List<ReturnTransaction> getAllReturns() {
        return returnRepository.findAll();
    }

    @Override
    public Optional<ReturnTransaction> getReturnTransactionById(UUID returnId) {
        return returnRepository.findById(returnId);
    }

}
