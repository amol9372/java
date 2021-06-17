package com.expensetracker.authservice.config;

import static java.util.Map.entry;

import java.security.Key;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.expensetracker.authservice.dto.UserDomain;

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

	public Key getSigingKey() {
		byte[] keyBytes = Decoders.BASE64.decode(key);
		Key key = Keys.hmacShaKeyFor(keyBytes);
		return key;
	}

	public String createToken(UserDomain userDomain) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		
		Map<String, String> claims = Map.ofEntries(entry("id", String.valueOf(userDomain.getId())),
				entry("name", userDomain.getName()), entry("username", userDomain.getUserName()));

		String jws = Jwts.builder().setIssuer("darkseid").setSubject(userDomain.getEmail()).setClaims(claims)
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(cal.getTime()).signWith(getSigingKey()).compact();

		return jws;
	}

	public Optional<Jws<Claims>> validateToken(String jwt) {
		return Optional.ofNullable(getClaims(jwt));
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
