package com.franco.gymplanner.users.service;

import com.franco.gymplanner.users.web.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto.Response createUser(UserDto.CreateRequest dto);
    UserDto.Response approveTrainer(Long userId);
    UserDto.Response rejectTrainer(Long userId);
    List<UserDto.Response> listPendingTrainers();
    UserDto.Response getById(Long id);
}