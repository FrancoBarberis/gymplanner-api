package com.franco.gymplanner.users.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.franco.gymplanner.users.model.AccountStatus;
import com.franco.gymplanner.users.model.Role;
import com.franco.gymplanner.users.model.User;
import com.franco.gymplanner.users.repo.UserRepository;
import com.franco.gymplanner.users.service.UserService;
import com.franco.gymplanner.users.web.dto.UserDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto.Response createUser(UserDto.CreateRequest dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        User user = new User();
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setPasswordHash(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());
        user.setEnabled(true);

        // Si es TRAINER, poner pendiente de aprobación, si es STUDENT activar de una
        if (dto.role() == Role.TRAINER) {
            user.setStatus(AccountStatus.PENDING_APPROVAL);
        } else {
            user.setStatus(AccountStatus.ACTIVE);
        }

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public UserDto.Response approveTrainer(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        user.setStatus(AccountStatus.ACTIVE);
        user.setEnabled(true);
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public UserDto.Response rejectTrainer(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        user.setStatus(AccountStatus.REJECTED);
        user.setEnabled(false);
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public List<UserDto.Response> listPendingTrainers() {
        return userRepository.findByRoleAndStatus(Role.TRAINER, AccountStatus.PENDING_APPROVAL)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto.Response getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return toResponse(user);
    }

    private UserDto.Response toResponse(User u) {
        return new UserDto.Response(
                u.getId(),
                u.getFullName(),
                u.getEmail(),
                u.getRole(),
                u.getStatus()
        );
    }
}
