package com.example.prenotazione_corse_autobus.controller;

import com.example.prenotazione_corse_autobus.entity.User;
import com.example.prenotazione_corse_autobus.entity.UserRepository;
import com.example.prenotazione_corse_autobus.security.AuthResponse;
import com.example.prenotazione_corse_autobus.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    ProjectUserService userService;

    //POST http://localhost:8082/users/register
    @PostMapping(path = "/register")
    public @ResponseBody User register(@RequestBody User user) {
        return userService.addUser(user);
    }

    //GET http://localhost:8082:/users/id/{id}
    @GetMapping (path = "/{id}")
    public @ResponseBody User getUser(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PostMapping(path = "/login")
    public @ResponseBody AuthResponse login(@RequestBody User user) {
        return userService.login(user);
    }


    //PATCH http://localhost:8082/users/{id}/credit/toup



    @PostMapping(path = "/re-auth")
    public @ResponseBody AuthResponse reAuth (@RequestHeader String refreshToken) throws Exception {
        return userService.reAuth(refreshToken);
    }

}

