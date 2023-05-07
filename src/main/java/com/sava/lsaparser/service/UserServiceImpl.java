package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.UserDto;
import com.sava.lsaparser.entity.User;
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
        User created = userRepository.save(userMapper.dtoToEntity(user));
        return userMapper.entityToDto(created);
    }
}
