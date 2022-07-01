package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.UserDto;

import java.util.List;

public interface UserService {
    /**
     * Retrieves all existing users
     * @return a list of {@link UserDto}s
     */
    List<UserDto> getAll();

    /**
     * Creates a user in DB
     * @param user to be persisted
     * @return created user
     */
    UserDto create(UserDto user);
}
