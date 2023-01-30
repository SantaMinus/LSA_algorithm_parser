package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.UserDto;
import com.sava.lsaparser.entity.UserEntity;
import com.sava.lsaparser.exception.UserAlreadyExistsException;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    /**
     * Retrieves all existing users
     *
     * @return a list of {@link UserDto}s
     */
    List<UserDto> getAll();

    /**
     * Creates a user in DB
     *
     * @param user to be persisted
     * @return created user
     */
    UserDto create(UserDto user);

    /**
     * Creates a new account for a user if it doesn't exist
     *
     * @param userDto to create an account for
     * @return a created user
     */
    UserEntity registerNewUserAccount(@Valid UserDto userDto) throws UserAlreadyExistsException;
}
