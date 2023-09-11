package ee.katrina.videorental.service;

import ee.katrina.videorental.dto.CustomerDTO;
import ee.katrina.videorental.entity.Customer;
import ee.katrina.videorental.mappers.CustomerMapper;
import ee.katrina.videorental.model.Role;
import ee.katrina.videorental.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public List<CustomerDTO> listAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID customerId) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(
                customerRepository.findById(customerId).orElse(null)));

    }

    @Override
    public CustomerDTO addCustomer(Customer customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
        customer.setRole(Role.CUSTOMER);
        return customerMapper.customerToCustomerDto(customerRepository
                .save(customer));
    }

    @Override
    public boolean deleteCustomerById(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
        if (customerRepository.findById(customerId).isEmpty()) {
            return Optional.empty();
        }
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        customerDTO.setId(foundCustomer.get().getId());
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.save(
                customerMapper.customerDtoToCustomer(customerDTO))));
    }

//    @Override
//    public Customer newSignup(Customer customer) {
//        customer.setPassword(encoder.encode(customer.getPassword()));
//        customer.setRole(Role.CUSTOMER);
//        return customerRepository.save(customer);
//    }
}
