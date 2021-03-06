package com.expensetracker.gateway.config;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {

    @Value("${jwt-key}")
    public String key;

    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    public String createToken() {

        String jws = Jwts.builder().setIssuer("Stormpath").setSubject("msilverman").claim("name", "Micah Silverman")
                .claim("scope", "admins").setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
                .setExpiration(Date.from(Instant.ofEpochSecond(1622470422L))).signWith(
                getSigningKey()).compact();

        return jws;
    }

    public Optional<Jws<Claims>> validateToken(String jwt) {
        return Optional.ofNullable(getClaims(jwt));
    }

    public Jws<Claims> getClaims(String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt);
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | IllegalArgumentException e) {

            e.printStackTrace();
        }
        return claims;
    }

}
