package com.nt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nt.service.JWTService;
import com.nt.serviceimpl.JWTServiceImpl;
import com.nt.serviceimpl.UserPrincipalServiceImpl;

@Component
public class JWTFilter extends OncePerRequestFilter {
	
	
	
	@Autowired
	private JWTService jwtService;
	
	
	@Autowired
	private ApplicationContext context;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsernameByToken(token);
		}
		
	Authentication ctx =  SecurityContextHolder.getContext().getAuthentication();
		
		if(username != null && ctx == null) {
			
            // Load user details from the database or in-memory store

			UserDetails userDetails = context.getBean(UserPrincipalServiceImpl.class).loadUserByUsername(username);
			
			if(jwtService.validateToken(token, userDetails)) {
				
				// If valid, set authentication in SecurityContext
				
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);

			}
		}
		filterChain.doFilter(request, response);
	}
}
