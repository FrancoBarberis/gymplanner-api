package com.franco.gymplanner.users.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franco.gymplanner.users.model.User;
import com.franco.gymplanner.users.model.AccountStatus;
import com.franco.gymplanner.users.model.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRoleAndStatus(Role role, AccountStatus status);
}