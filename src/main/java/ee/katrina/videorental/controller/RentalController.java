package ee.katrina.videorental.controller;

import ee.katrina.videorental.entity.RentalTransaction;
import ee.katrina.videorental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class RentalController {

    public static final String RENTAL_TRANSACTION = "/api/v1/rental";
    public static final String RENTAL_TRANSACTION_ID = RENTAL_TRANSACTION + "/{rentalId}";

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
    public ResponseEntity addRental(@RequestBody RentalTransaction rentalTransaction) {
        RentalTransaction savedRentalTransaction = rentalService.addRental(rentalTransaction);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", RENTAL_TRANSACTION + "/" + savedRentalTransaction.getId().toString());
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
    public ResponseEntity updateRentalById(@PathVariable UUID rentalId, @RequestBody RentalTransaction rentalTransaction) {
        if (rentalService.updateRentalById(rentalId, rentalTransaction).isEmpty()) {
            throw new RuntimeException("Rental not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
