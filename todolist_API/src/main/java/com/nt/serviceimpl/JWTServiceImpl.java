package com.nt.serviceimpl;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.nt.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {

	private String secretKey = "";

	public JWTServiceImpl() {

		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
			// key is genereated into byte[] anf then string
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public String generateToken(String username) {

		Map<String, Object> claims = new HashMap<String, Object>();

		return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30 * 1000)).and().signWith(getKey())
				.compact();

	}

	@Override
	public String extractUsernameByToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		try {

			// String username = Jwts.parser()
			// .build()
			// .parseSignedClaims(token)
			// .getPayload()
			// .getSubject();

			return Jwts.parser() // Create a JWT parser builder
					.verifyWith(getKey()) // Set the key used to sign the token (important for verifying the token's
											// integrity)
					.build() // Finalize and build the parser
					.parseSignedClaims(token) // Parse the token and validate its signature. Returns a Jws<Claims>
												// object
					.getPayload();
			// Extract claims
		} catch (io.jsonwebtoken.security.SecurityException | io.jsonwebtoken.MalformedJwtException e) {
			// Thrown if the token's signature is invalid or malformed
			throw new RuntimeException("Invalid JWT signature or token is malformed.", e);
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			// Thrown if the token has expired
			throw new RuntimeException("JWT token is expired.", e);
		} catch (io.jsonwebtoken.UnsupportedJwtException e) {
			// Thrown if the token is unsupported
			throw new RuntimeException("JWT token is unsupported.", e);
		} catch (IllegalArgumentException e) {
			// Thrown if the token is null or empty
			throw new RuntimeException("JWT claims string is empty.", e);
		}
	}

	@Override
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsernameByToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return expirationTime(token).before(new Date());
	}

	private Date expirationTime(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
