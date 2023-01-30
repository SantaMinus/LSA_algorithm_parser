package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.UserDto;
import com.sava.lsaparser.entity.UserEntity;
import com.sava.lsaparser.exception.UserAlreadyExistsException;
import com.sava.lsaparser.mapper.UserMapper;
import com.sava.lsaparser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAll() {
        return userMapper.entityListToDtoList(userRepository.findAll());
    }

    @Override
    public UserDto create(UserDto user) {
        UserEntity created = userRepository.save(userMapper.dtoToEntity(user));
        return userMapper.entityToDto(created);
    }

    @Override
    public UserEntity registerNewUserAccount(UserDto userDto) throws UserAlreadyExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException(String.format("Email %s is already registered", userDto.getEmail()));
        }
        // TODO: register
        return null;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
