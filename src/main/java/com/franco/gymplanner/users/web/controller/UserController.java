package com.franco.gymplanner.users.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.franco.gymplanner.users.service.UserService;
import com.franco.gymplanner.users.web.dto.UserDto;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // Inyección por constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/v1/users/ping
    @GetMapping("/ping")
    public String ping() {
        return "Feature de usuarios funcionando correctamente";
    }

    // POST /api/v1/users/add
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json", name = "createUserMapping")
    public ResponseEntity<UserDto.Response> create(@Valid @RequestBody UserDto.CreateRequest dto) {
        var resp = userService.createUser(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + resp.id()))
                .body(resp);
    }

    // GET /api/v1/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    // --- Endpoints solo para ADMIN ---

    // Lista de entrenadores pendientes de aprobación
    @GetMapping("/pending-trainers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto.Response>> listPendingTrainers() {
        return ResponseEntity.ok(userService.listPendingTrainers());
    }

    // Aprobar entrenador
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto.Response> approveTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(userService.approveTrainer(id));
    }

    // Rechazar entrenador
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto.Response> rejectTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(userService.rejectTrainer(id));
    }

    // GET /api/v1/users/students/pending-routines
    @GetMapping("/students/pending-routines")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<UserDto.Response>> listStudentsWithPendingRoutines() {
        return ResponseEntity.ok(userService.listStudentsWithPendingRoutines());
    }
}