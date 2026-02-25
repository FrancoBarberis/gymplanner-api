package com.franco.gymplanner.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.franco.gymplanner.users.service.UserService;
import com.franco.gymplanner.users.web.dto.UserDto;

@RestController                      // Declara que esta clase expone endpoints REST
@RequestMapping("/api/v1/users")     // Ruta base del controller
public class UserController {

    @Autowired
    private UserService userService;

    //TODO: Cambiar a leer usuarios
    @GetMapping("/ping")             // GET /api/v1/users/ping
    public String ping() {
        return "API funcionando correctamente";
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto.Response> create(@Valid @RequestBody UserDto.CreateRequest dto) {
        // 1. Controller recibe la solicitud (validada por @Valid)
        // 2. Delega al service
        // 3. Retorna la respuesta
        UserDto.Response response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}