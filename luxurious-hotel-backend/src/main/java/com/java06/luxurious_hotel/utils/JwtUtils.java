package com.java06.luxurious_hotel.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.java06.luxurious_hotel.dto.AuthorityDTO;
import com.java06.luxurious_hotel.exception.authen.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.key}")
    private String secretKeyString;

    private final long EXPIRATION_TIME = 365 * 24 * 60 * 60 * 1000;

    public String generateJwtToken(AuthorityDTO authorityDTO) {
        var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));

        return Jwts.builder()
                .subject(authorityDTO.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("role", authorityDTO.getRole())
                .signWith(secretKey)
                .compact();
    }

    public AuthorityDTO verifyToken(String token) throws JsonProcessingException {

        AuthorityDTO authorityDTO = new AuthorityDTO();

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyString));
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);


        String role = claims.getPayload().get("role", String.class);

        authorityDTO.setUsername(claims.getPayload().getSubject());
        authorityDTO.setRole(role);
        return authorityDTO;


    }
}
