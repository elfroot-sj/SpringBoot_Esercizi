package com.example.math_upgraded_es3;
//qui creo una configurazione che definisce gli utenti in memoria


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration //this annotation means that this class is used for configuration purposes and tells Spring that this class contains "bean" definitions
public class SecurityConfig {

    // Definisco utenti in memoria
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}adminpass") // {noop} = no password encoding -> WARNING: only for testing, NOT for production phase
                .roles("ADMIN")
                .build();

        UserDetails customer = User
                .withUsername("customer")
                .password("{noop}custpass")
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(admin, customer);
    }

    // Configuro regole di accesso
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // solo ADMIN
                        .requestMatchers("/public/**").permitAll()      // accesso libero
                        .anyRequest().authenticated()                   // tutte le altre richiedono login
                )

                .formLogin(withDefaults())   // abilita login con form predefinito
                .httpBasic(withDefaults()); // abilita login basic (per Postman / curl)


        return http.build();
    }
}
