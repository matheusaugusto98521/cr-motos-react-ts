package com.example.cr_motos_backend.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfigurations {

    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // PERMISSÕES PARA ADMNISTRADOR
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        // PERMISSÕES PARA USUÁRIO
                        .requestMatchers(HttpMethod.PUT, "/user/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN", "USER")
                        // PERMISSÕES PARA AUTENTICAÇÃO
                        .requestMatchers(HttpMethod.POST, "/auth/init-reset-password").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/user/reset-password").hasAnyRole("ADMIN", "USER")
                        // PERMISSÕES PARA MARCAS
                        .requestMatchers(HttpMethod.POST, "/brand/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/brand/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/brand/**").hasRole("ADMIN")
                        // PERMISSÕES PARA PEÇAS
                        .requestMatchers(HttpMethod.POST, "/part/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/part/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/part/**").hasRole("ADMIN")
                        // PERMISSÕES PARA SERVIÇOS
                        .requestMatchers(HttpMethod.POST, "/service/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/service/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/service/**").hasRole("ADMIN")
                        // PERMISSÕES PARA MOTOS
                        .requestMatchers(HttpMethod.POST, "/motorcicle/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/motorcicle/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/motorcicle/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/motorcicle/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
