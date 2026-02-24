package com.franco.gymplanner.users.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.franco.gymplanner.users.web.dto.UserDto;

@RestController                      // Declara que esta clase expone endpoints REST
@RequestMapping("/api/v1/users")     // Ruta base del controller
public class UserController {

    @GetMapping("/ping")             // GET /api/v1/users/ping
    public String ping() {
        return "API funcionando correctamente";
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@Valid @RequestBody UserDto.CreateRequest dto) {
        // Si llegamos acá, dto ya fue validado por Bean Validation
        return ResponseEntity.ok().build();
    }
}
``