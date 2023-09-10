package ee.katrina.videorental.controller;

import ee.katrina.videorental.dto.security.AuthToken;
import ee.katrina.videorental.dto.security.LoginData;
import ee.katrina.videorental.entity.Customer;
import ee.katrina.videorental.repository.CustomerRepository;
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
    CustomerRepository customerRepository;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    CustomerService customerService;

    @PostMapping("login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginData loginData) throws RuntimeException {
        Customer customer = customerRepository.findByUsername(loginData.getUsername());
        if (customer == null) {
            throw new RuntimeException("Wrong username or password");
        }
        System.out.println(loginData.getPassword());
        System.out.println(customer.getPassword());
        if (!encoder.matches(loginData.getPassword(), customer.getPassword())) {
            System.out.println("EXCEPTION THROWING");
            throw new RuntimeException("Wrong username or password");
        }
        return new ResponseEntity<>(tokenGenerator.getToken(customer), HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<AuthToken> signup(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.newSignup(customer);
        return new ResponseEntity<>(tokenGenerator.getToken(savedCustomer), HttpStatus.OK);
    }

}
