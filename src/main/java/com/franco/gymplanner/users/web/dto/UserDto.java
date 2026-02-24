package com.franco.gymplanner.users.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {

    // Lo que el cliente (React) envía para crear un usuario
    public record CreateRequest(

            @NotBlank(message = "El email es obligatorio") 
            @Email(message = "Email inválido") 
            String email,

            @NotBlank(message = "La contraseña es obligatoria")
            @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
            String password,

            @NotBlank(message = "El nombre es obligatorio")
            String fullName,

            @NotBlank 
            String role
    ) {}

    // Lo que devolvemos al cliente (nunca devolver password)
    public record Response(
            String id,
            String email,
            String fullName,
            String role
    ) {}
}