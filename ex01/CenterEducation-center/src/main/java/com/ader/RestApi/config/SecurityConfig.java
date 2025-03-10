package com.ader.RestApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ader.RestApi.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
/*
 * The class configuration that will bundle all the security components and
 * configurations
 */
public class SecurityConfig {
        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/auth/**").permitAll()
                                                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/api-docs/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()
                                                // Optional: Add URL-based role restrictions, URL-based security in
                                                // SecurityConfig
                                                .requestMatchers(HttpMethod.GET, "/**").authenticated()
                                                .requestMatchers(HttpMethod.POST, "/**").hasAuthority("ADMINISTRATOR")
                                                .requestMatchers(HttpMethod.PUT, "/**").hasAuthority("ADMINISTRATOR")
                                                .requestMatchers(HttpMethod.DELETE, "/**").hasAuthority("ADMINISTRATOR")
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}

// 1. URL-based security in SecurityConfig
// 2. Method-level security with @PreAuthorize annotations
// The combination of both provides robust security, allowing you to control
// access to different parts of your application based on both the URL and the
// user's role.

//Session based authentication
//JWT based authentication

// stateful : the request share the same session context
// stateless : the request is independent of each other

// JWT is stateless
// Session is stateful
