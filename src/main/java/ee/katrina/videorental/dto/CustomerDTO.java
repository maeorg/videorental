package ee.katrina.videorental.dto;

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

}
