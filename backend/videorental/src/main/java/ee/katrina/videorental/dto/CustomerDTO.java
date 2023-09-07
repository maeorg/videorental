package ee.katrina.videorental.dto;

import ee.katrina.videorental.entity.ContactData;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long bonusPoints;

    @OneToOne(cascade = {jakarta.persistence.CascadeType.ALL})
    private ContactData contactData;


}
