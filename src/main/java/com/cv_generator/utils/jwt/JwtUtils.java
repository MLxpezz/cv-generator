package com.cv_generator.utils.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateToken(Authentication authentication) {

        Instant now = Instant.now();
        Instant expirationDate = now.plus(Long.parseLong(expiration), ChronoUnit.MILLIS);

        String email = authentication.getName();

        return Jwts
                .builder()
                .subject(email)
                .expiration(Date.from(expirationDate))
                .issuedAt(Date.from(now))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            System.out.println("RETORNO TRUE");
            return true;
        }catch (JwtException e) {
            System.out.println("RETORNO FALSO");
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public SecretKey getSecretKey() {
        //String secretKey = System.getenv("SECRET_KEY");
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}
