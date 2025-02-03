package com.jphilips.taskmanagementv2.services;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	
	private static final String SECRET = "E42C30CEA56B1E627005CDFC501D02496ECD802B018908AAADB7DFDF53B5B734ED295724B7EC091F94A4D460E0C418FFAA7EFA854327B00F8AE25918D851B52F";
	private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);
	
	public String generateToken(String username) {
		Map<String, String> claims = new HashMap<String, String>();
		claims.put("iss", "testAddIss");
		claims.put("name", username);
		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(generateKey())
				.compact(); // converts to JSONFORMAT
	}
	
	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(SECRET);
		return Keys.hmacShaKeyFor(decodedKey);
	}
	
	public String extractUsername(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getSubject();
	}
	
	private Claims getClaims(String jwt)  {
		Claims claims = Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
		return claims;
	}
	
	public JwtException tokenMissing() {
		return new JwtException("Missing Token");
	}
	
	public JwtException expiredToken() {
		return new JwtException("Expired Token");
	}
	public JwtException invalidToken() {
		return new JwtException("Invalid Token");
	}
	
	public JwtException otherException() {
		return new JwtException("Unexpected error occurred");
	}
	
}
