package ee.katrina.videorental.service;

import ee.katrina.videorental.dto.CustomerDTO;
import ee.katrina.videorental.entity.Customer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> listAllCustomers();

    Optional<CustomerDTO> getCustomerById(UUID customerId);

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    boolean deleteCustomerById(UUID customerId);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO);

    Customer newSignup(Customer customer);
}
