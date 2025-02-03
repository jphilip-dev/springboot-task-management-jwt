package com.jphilips.taskmanagementv2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jphilips.taskmanagementv2.services.MyUserService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private MyUserService myUserService;
	
	@Autowired 
	private JWTAuthenticationFilter authenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
		return http.authorizeHttpRequests(registry -> {
			// public end points
			registry.requestMatchers(HttpMethod.POST, "/api/auth/register","/api/auth/login").permitAll();
			
			// tasks end points
			registry.requestMatchers(HttpMethod.GET,"/api/tasks/**").hasRole("MEMBER");
			registry.requestMatchers(HttpMethod.PUT,"/api/tasks/**").hasRole("MEMBER");
			
			registry.requestMatchers(HttpMethod.POST,"/api/tasks/**").hasRole("LEAD");
			registry.requestMatchers(HttpMethod.DELETE,"/api/tasks/**").hasRole("LEAD");
			
			// admin 
			registry.requestMatchers("/api/admin/**").hasRole("ADMIN");
			
			registry.anyRequest().authenticated();
		})
			//.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
			.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.csrf(csrf-> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set stateless
			
			.build();
		
	}
	
	@Bean
	public UserDetailsService detailsService() {
		
		return myUserService;

		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(myUserService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
    public AuthenticationManager authenticationManager() {
    	return new ProviderManager(authenticationProvider());
    }
	
	
}



//
//UserDetails member = User.builder()
//		.username("member")
//		.password(passwordEncoder().encode("1234"))
//		.roles("MEMBER")
//		.build();
//
//UserDetails lead = User.builder()
//		.username("lead")
//		.password(passwordEncoder().encode("1234"))
//		.roles("LEAD", "MEMBER")
//		.build();
//
//return new InMemoryUserDetailsManager(member,lead);
