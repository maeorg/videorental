package ee.katrina.videorental.controller;

import ee.katrina.videorental.entity.RentalTransaction;
import ee.katrina.videorental.entity.RentalTransactionLine;
import ee.katrina.videorental.entity.ReturnTransaction;
import ee.katrina.videorental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RentalController {

    public static final String RENTAL_TRANSACTION = "/api/v1/rental";
    public static final String RENTAL_TRANSACTION_ID = RENTAL_TRANSACTION + "/{rentalId}";

    public static final String RETURN_TRANSACTION = "/api/v1/return";
    public static final String RETURN_TRANSACTION_ID = RETURN_TRANSACTION + "/{returnId}";
    public static final String RETURN_MOVIE_ID = RETURN_TRANSACTION + "/{movieId}";

    @Autowired
    RentalService rentalService;

    @GetMapping(value = RENTAL_TRANSACTION)
    public ResponseEntity getAllRentals() {
        List<RentalTransaction> rentalTransactions = rentalService.getAllRentals();
        return new ResponseEntity(rentalTransactions, HttpStatus.OK);
    }

    @GetMapping(value = RENTAL_TRANSACTION_ID)
    public ResponseEntity getRentalById(@PathVariable UUID rentalId) {
        RentalTransaction rentalTransaction = rentalService.getRentalById(rentalId).orElseThrow(RuntimeException::new);
        return new ResponseEntity(rentalTransaction, HttpStatus.OK);
    }

    @PostMapping(value = RENTAL_TRANSACTION)
    public ResponseEntity addRental(@Validated @RequestBody RentalTransaction rentalTransaction) {
        RentalTransaction savedRentalTransaction = rentalService.addRental(rentalTransaction);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", RENTAL_TRANSACTION + "/" +
                savedRentalTransaction.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = RENTAL_TRANSACTION_ID)
    public ResponseEntity deleteRentalById(@PathVariable UUID rentalId) {
        if (!rentalService.deleteRentalById(rentalId)) {
            throw new RuntimeException("Rental not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(RENTAL_TRANSACTION_ID)
    public ResponseEntity updateRentalById(@PathVariable UUID rentalId,
                                           @Validated @RequestBody RentalTransaction rentalTransaction) {
        if (rentalService.updateRentalById(rentalId, rentalTransaction).isEmpty()) {
            throw new RuntimeException("Rental not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = RETURN_MOVIE_ID)
    public ResponseEntity returnMovie(@PathVariable UUID movieId) {
        double lateFee = rentalService.returnMovieAndCalculateLateFees(movieId);
        return new ResponseEntity(lateFee, HttpStatus.OK);
    }

    @GetMapping(RETURN_TRANSACTION)
    public ResponseEntity getAllReturns() {
        List<ReturnTransaction> returns = rentalService.getAllReturns();
        return new ResponseEntity(returns, HttpStatus.OK);
    }

    @GetMapping(RETURN_TRANSACTION_ID)
    public ResponseEntity getReturnTransactionById(@PathVariable UUID returnId) {
        Optional<ReturnTransaction> returnTransaction = rentalService.getReturnTransactionById(returnId);
        return new ResponseEntity<>(returnTransaction, HttpStatus.OK);
    }

}
