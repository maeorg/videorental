package ee.katrina.videorental.security;

import ee.katrina.videorental.dto.security.AuthToken;
import ee.katrina.videorental.entity.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    @Value("${auth.security.key}")
    String authSecurityKey;

    public AuthToken getToken(Customer customer) {
        AuthToken authToken = new AuthToken();

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        authToken.setExpiration(expiration);

        String jwtToken = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(authSecurityKey)),
                        SignatureAlgorithm.HS512)
                .setIssuer("Katrina's videorental")
                .setExpiration(expiration)
                .setSubject(customer.getId().toString())
                .setAudience(String.valueOf(customer.isAdmin()))
                .compact();

        authToken.setToken(jwtToken);

        return authToken;
    }
}
