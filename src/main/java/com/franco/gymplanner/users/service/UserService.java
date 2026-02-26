package com.franco.gymplanner.users.service;

import com.franco.gymplanner.users.model.AccountStatus;
import com.franco.gymplanner.users.model.Role;
import com.franco.gymplanner.users.model.User;
import com.franco.gymplanner.users.repo.UserRepository;
import com.franco.gymplanner.users.web.dto.UserDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto.Response createUser(UserDto.CreateRequest dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        User user = new User();
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setPasswordHash(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());

        if (dto.role() == Role.TRAINER) {
            user.setStatus(AccountStatus.PENDING_APPROVAL);
            user.setEnabled(false);
        } else {
            user.setStatus(AccountStatus.ACTIVE);
            user.setEnabled(true);
        }

        user = userRepository.save(user);
        return toResponse(user);
    }

    public List<UserDto.Response> listPendingTrainers() {
        return userRepository
                .findByRoleAndStatus(Role.TRAINER, AccountStatus.PENDING_APPROVAL)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserDto.Response> listStudentsWithPendingRoutines() {
        // TODO: implementar cuando RoutineRequest esté lista
        return List.of();
    }

    public UserDto.Response approveTrainer(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        u.setStatus(AccountStatus.ACTIVE);
        u.setEnabled(true);

        return toResponse(u);
    }

    public UserDto.Response getById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return toResponse(user);
    }

    public UserDto.Response rejectTrainer(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        u.setStatus(AccountStatus.REJECTED);
        u.setEnabled(false);

        return toResponse(u);
    }

    public UserDto.Response banUser(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        u.setStatus(AccountStatus.SUSPENDED);
        u.setEnabled(false);

        return toResponse(u);
    }

    public UserDto.Response unbanUser(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        u.setStatus(AccountStatus.ACTIVE);
        u.setEnabled(true);

        return toResponse(u);
    }

    private UserDto.Response toResponse(User u) {
        return new UserDto.Response(
                u.getId(),
                u.getFullName(),
                u.getEmail(),
                u.getRole(),
                u.getStatus());
    }
}