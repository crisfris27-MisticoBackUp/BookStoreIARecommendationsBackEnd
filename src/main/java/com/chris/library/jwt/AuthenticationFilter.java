package com.chris.library.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{

	/**
	 * 
	 	1.-Gets "Authorization" header from request
		2.-Checks if it starts with "Bearer "
		3.-Extracts token by removing "Bearer " (7 characters)
		4.-Validates token and extracts username
		5.-Sets authentication in Spring Security context
	 * 
	 * */
	
	
	//getting the bean of the class JWTtokens
    @Autowired
    private JWTtokens jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
    	
        // 1. Extract Authorization header
        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("=== JWT Filter Triggered ===");
        System.out.println("URI: " + request.getRequestURI());
        
        System.out.println("Auth Header: " + authorizationHeader);

        String username = null;
        String jwt = null;
        
        // 2. Check if header exists and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 3. Extract token (remove "Bearer " prefix)
            jwt = authorizationHeader.substring(7);

            try {
                // 4. Extract username from token
                username = jwtUtil.extractUsername(jwt);
                System.out.println("Extracted username: " + username);

            } catch (Exception e) {
                System.out.println("Error extracting username from token: " + e.getMessage());
            }
        }
        
        // 5. Validate token and set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            if (jwtUtil.validateToken(jwt)) {
                // Create authentication token
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // Continue filter chain
        filterChain.doFilter(request, response);
    }

}
