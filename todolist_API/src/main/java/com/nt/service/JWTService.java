package com.nt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JWTService {

	String generateToken(String username);

	String extractUsernameByToken(String token);

	boolean validateToken(String token, UserDetails userDetails);

	
}
