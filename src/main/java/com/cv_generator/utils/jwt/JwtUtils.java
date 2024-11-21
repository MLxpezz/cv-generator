package com.cv_generator.utils.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.expiration}")
    private String expiration;

    private SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String email) {

        Instant now = Instant.now();
        Instant expirationDate = now.plus(Long.parseLong(expiration), ChronoUnit.MILLIS);

        return Jwts
                .builder()
                .subject(email)
                .expiration(Date.from(now))
                .issuedAt(Date.from(expirationDate))
                .signWith(key)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return true;
        }catch (JwtException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
