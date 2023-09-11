package ee.katrina.videorental.controller;

import ee.katrina.videorental.dto.security.AuthToken;
import ee.katrina.videorental.dto.security.LoginData;
import ee.katrina.videorental.entity.Customer;
import ee.katrina.videorental.entity.Employee;
import ee.katrina.videorental.repository.CustomerRepository;
import ee.katrina.videorental.repository.EmployeeRepository;
import ee.katrina.videorental.security.TokenGenerator;
import ee.katrina.videorental.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginData loginData) throws RuntimeException {
        Customer customer = customerRepository.findByUsername(loginData.getUsername());
        Employee employee = employeeRepository.findByUsername(loginData.getUsername());
        if (customer == null && employee == null) {
            throw new RuntimeException("Wrong username or password");
        }
        if (customer != null) {
            if (!encoder.matches(loginData.getPassword(), customer.getPassword())) {
                throw new RuntimeException("Wrong username or password");
            }
            return new ResponseEntity<>(tokenGenerator.getTokenForCustomer(customer), HttpStatus.OK);
        }
        if (!encoder.matches(loginData.getPassword(), employee.getPassword())) {
            throw new RuntimeException("Wrong username or password");
        }
        return new ResponseEntity<>(tokenGenerator.getTokenForEmployee(employee), HttpStatus.OK);
    }

//    @PostMapping("signup")
//    public ResponseEntity<AuthToken> signup(@RequestBody Customer customer) {
//        Customer savedCustomer = customerService.add(customer);
//        return new ResponseEntity<>(tokenGenerator.getToken(savedCustomer), HttpStatus.OK);
//    }

}
