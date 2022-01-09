package com.expensetracker.userservice.config;

import static java.util.Map.entry;

import com.expensetracker.userservice.entities.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt-key}")
  public String key;

  public Key getSigingKey() {
    byte[] keyBytes = Decoders.BASE64.decode(key);
    Key key = Keys.hmacShaKeyFor(keyBytes);
    return key;
  }

  public String createToken(UserEntity userDomain) {

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.HOUR_OF_DAY, 1);

    Map<String, String> claims = Map.ofEntries(
        entry("email", String.valueOf(userDomain.getEmail()))
      //  entry("name", userDomain.getUsername()), entry("username", userDomain.getUsername())
    );

    String jws = Jwts.builder().setIssuer("darkseid").setSubject(userDomain.getEmail())
        .setClaims(claims)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(cal.getTime()).signWith(getSigingKey()).compact();

    return jws;
  }

  public Jws<Claims> getClaims(String jwt) {
    Jws<Claims> claims = null;
    try {
      claims = Jwts.parserBuilder().setSigningKey(getSigingKey()).build().parseClaimsJws(jwt);
    } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
        | IllegalArgumentException e) {

      e.printStackTrace();
    }
    return claims;
  }

}
