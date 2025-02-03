package com.jphilips.taskmanagementv2.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.jphilips.taskmanagementv2.services.JWTService;
import com.jphilips.taskmanagementv2.services.MyUserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private MyUserService myUserService;
	
	@Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Skip token check public end points (eg.. login, register)
	    if (request.getRequestURI().startsWith("/api/auth")) {
	        filterChain.doFilter(request, response);
	        return; 
	    }
		

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			
			//resolver.resolveException(request, response, null, jwtService.tokenMissing());
			handleException(request, response, jwtService.tokenMissing());
			return;
			
		}
		
		String jwt = authHeader.substring(7); // extracts jwt from header
		
		try {
				
			String username = jwtService.extractUsername(jwt);
			UserDetails userDetails = myUserService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					username, userDetails.getPassword(), userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
		}catch (ExpiredJwtException e) {
			//resolver.resolveException(request, response, null, jwtService.expiredToken());
			handleException(request, response, jwtService.expiredToken());
			return;		
				
		} catch (SignatureException e) {
			//resolver.resolveException(request, response, null, jwtService.invalidToken());
			handleException(request, response, jwtService.invalidToken());
			return;
		
		} catch (Exception e) {
			//resolver.resolveException(request, response, null, jwtService.otherException());
			handleException(request, response, jwtService.otherException());
			return;
		}
		
		
		filterChain.doFilter(request, response);
		

	}
	
	private void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
	    resolver.resolveException(request, response, null, ex);
	}

}
