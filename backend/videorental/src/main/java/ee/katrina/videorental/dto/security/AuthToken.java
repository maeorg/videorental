package ee.katrina.videorental.dto.security;

import lombok.Data;

import java.util.Date;

@Data
public class AuthToken {
    private String token;
    private Date expiration;
}
