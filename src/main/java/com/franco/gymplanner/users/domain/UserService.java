package com.franco.gymplanner.users.domain;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Long createUser(CreateUserRequest dto);
}
