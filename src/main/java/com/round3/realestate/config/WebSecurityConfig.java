package com.round3.realestate.config;

import com.round3.realestate.security.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                auth -> auth
                    .requestMatchers( "/error").permitAll()
                    .requestMatchers(HttpMethod.GET, "/health").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/scrape").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auction/create").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/auction/*").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auction/*").permitAll()
                    .requestMatchers(HttpMethod.PATCH, "/api/auction/*/close").permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint))
            .authenticationProvider(authenticationProvider)
            .sessionManagement(session -> session.
                sessionCreationPolicy(SessionCreationPolicy.NEVER))
            .addFilterBefore(
                jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}