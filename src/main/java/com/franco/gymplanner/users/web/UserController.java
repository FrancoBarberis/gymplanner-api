package com.franco.gymplanner.users.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.franco.gymplanner.users.service.UserService;
import com.franco.gymplanner.users.web.dto.UserDto;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // Inyección por constructor (recomendada)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/v1/users/ping
    @GetMapping(value = "/ping")
    public String ping() {
        return "API funcionando correctamente";
    }

    // POST /api/v1/users/add
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json", name = "createUserMapping")
    public ResponseEntity<UserDto.Response> create(@Valid @RequestBody UserDto.CreateRequest dto) {
        // 1) Validado el DTO por @Valid
        // 2) Delegamos la lógica al service
        UserDto.Response resp = userService.createUser(dto);

        // 3) Devolvemos 201 Created + Location + body de respuesta
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + resp.id()))
                .body(resp);
    }
}