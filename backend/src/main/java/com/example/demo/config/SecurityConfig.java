package com.example.demo.config;

import com.example.demo.jwt.JwtAccessTokenFilter;
import com.example.demo.jwt.JwtRefreshTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAccessTokenFilter jwtAccessTokenFilter;
    private final JwtRefreshTokenFilter jwtRefreshTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
//                        .requestMatchers("").hasRole("ADMIN") //TODO 추후 필요시 경로 추가
//                        .requestMatchers("").hasRole("USER") //TODO 추후 필요시 경로 추가
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAccessTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtRefreshTokenFilter, JwtAccessTokenFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
