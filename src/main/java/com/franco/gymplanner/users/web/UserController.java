package com.franco.gymplanner.users.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController                      // Declara que esta clase expone endpoints REST
@RequestMapping("/api/v1/users")     // Ruta base del controller
public class UserController {

    @GetMapping("/ping")             // GET /api/v1/users/ping
    public String ping() {
        return "API funcionando correctamente";
    }
    @PostMapping("/add")
}