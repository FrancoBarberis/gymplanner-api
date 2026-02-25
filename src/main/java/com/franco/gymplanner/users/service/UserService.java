package com.franco.gymplanner.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franco.gymplanner.users.domain.User;
import com.franco.gymplanner.users.repo.UserRepository;
import com.franco.gymplanner.users.web.dto.UserDto;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Crea un nuevo usuario en la base de datos
     */
    public UserDto.Response createUser(UserDto.CreateRequest dto) {
        // Validar que el email no exista
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        // Crear la entidad User
        User user = new User(
            dto.email(),
            dto.password(), // En producción: hasheada con BCrypt
            dto.fullName(),
            dto.role()
        );
        
        // Guardar en BD (repository)
        User savedUser = userRepository.save(user);
        
        // Retornar DTO sin password
        return new UserDto.Response(
            savedUser.getId().toString(),
            savedUser.getEmail(),
            savedUser.getFullName(),
            savedUser.getRole()
        );
    }
}
