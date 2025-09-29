package com.example.math_upgraded_es3;
//qui trovo tutti gli endpoint da poter testare

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/admin/secret")
    public String adminOnly() {
        return "Questa è una pagina segreta per ADMIN!";
    }

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Ciao a tutti! Accesso libero.";
    }

    @GetMapping("/user/profile")
    public String userProfile() {
        return "Profilo utente (serve login, qualsiasi ruolo).";
    }
}

