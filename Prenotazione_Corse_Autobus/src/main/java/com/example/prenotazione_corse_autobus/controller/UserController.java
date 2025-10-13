package com.example.prenotazione_corse_autobus.controller;

import com.example.prenotazione_corse_autobus.dto.CreditTopupRequest;
import com.example.prenotazione_corse_autobus.entity.User;
import com.example.prenotazione_corse_autobus.security.AuthResponse;
import com.example.prenotazione_corse_autobus.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class UserController {

    @Autowired
    ProjectUserService userService;

    //POST http://localhost:8082/register
    @PostMapping(path = "/register")
    public @ResponseBody User register(@RequestBody User user) {
        return userService.addUser(user);
    }

    //POST http://localhost:8082/login
    @PostMapping("/login")
    public @ResponseBody AuthResponse login(@RequestBody User user) {
        return userService.login(user);
    }

    //GET http://localhost:8082/me    (AUTENTICATO)
    @GetMapping("/me")
    public @ResponseBody User me(org.springframework.security.core.Authentication auth) {
        return userService.getByEmail(auth.getName())
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "User not found"));
    }

    //PATCH http://localhost:8082/me/credit/topup  (AUTENTICATO)
    @PatchMapping(path= "/me/credit/topup")
    public @ResponseBody User topupCredit(Authentication auth,
                                          @RequestBody CreditTopupRequest request) {
        return userService.topupCredit(auth.getName(), request.getAmount());
    }

    //POST http://localhost:8082/re-auth
    @PostMapping(path = "/re-auth")
    public @ResponseBody AuthResponse reAuth (@RequestHeader String refreshToken) throws Exception {
        return userService.reAuth(refreshToken);
    }







    //GET http://localhost:8082:/users/{id}
    /*@GetMapping (path = "/users/{id}")
    public @ResponseBody User getUser(@PathVariable Integer id){
        return userService.getUserById(id);
    }*/
}

