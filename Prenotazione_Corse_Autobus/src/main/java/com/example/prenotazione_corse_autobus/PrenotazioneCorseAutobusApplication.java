package com.example.prenotazione_corse_autobus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PrenotazioneCorseAutobusApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrenotazioneCorseAutobusApplication.class, args);
    }

    // Bean per l'encoding delle password
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
