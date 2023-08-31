package ee.katrina.videorental.mappers;

import ee.katrina.videorental.dto.CustomerDTO;
import ee.katrina.videorental.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customer);
}
