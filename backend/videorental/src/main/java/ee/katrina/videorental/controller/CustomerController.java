package ee.katrina.videorental.controller;

import ee.katrina.videorental.dto.CustomerDTO;
import ee.katrina.videorental.entity.Customer;
import ee.katrina.videorental.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    @Autowired
    CustomerService customerService;

    // get all customers
    @GetMapping(value = CUSTOMER_PATH)
    public ResponseEntity getAllCustomers() {
        List<CustomerDTO> customers = customerService.listAllCustomers();
        return new ResponseEntity(customers, HttpStatus.OK);
    }

    // get customer by id
    @GetMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity getCustomerById(@PathVariable UUID customerId) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId).orElseThrow(RuntimeException::new);
        return new ResponseEntity(customerDTO, HttpStatus.OK);
    }

    // add customer
    @PostMapping(value = CUSTOMER_PATH)
    public ResponseEntity addCustomer(@Validated @RequestBody Customer customer) {
        CustomerDTO savedCustomerDTO = customerService.addCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomerDTO.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    // remove customer
    @DeleteMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomerById(@PathVariable UUID customerId) {
        if (!customerService.deleteCustomerById(customerId)) {
            throw new RuntimeException("Customer not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // update customer
    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(@PathVariable UUID customerId,
                                             @Validated @RequestBody CustomerDTO customerDTO) {
        if (customerService.updateCustomerById(customerId, customerDTO).isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
