package com.franco.gymplanner.users.web.dto;

import com.franco.gymplanner.users.model.AccountStatus;
import com.franco.gymplanner.users.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class UserDto {

    public record CreateRequest(
            @NotBlank @Size(max = 120) String fullName,
            @NotBlank @Email @Size(max = 180) String email,
            @NotBlank String password,
            @NotNull Role role // STUDENT o TRAINER (ADMIN solo lo crea un ADMIN)
    ) {}

    public record Response(
            Long id,
            String fullName,
            String email,
            Role role,
            AccountStatus status
    ) {}

    public record UpdateRoleRequest(
            @NotNull Role role
    ) {}
}
