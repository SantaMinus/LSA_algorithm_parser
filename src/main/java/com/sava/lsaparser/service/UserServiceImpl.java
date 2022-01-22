package com.sava.lsaparser.service;

import com.sava.lsaparser.dto.UserDto;
import com.sava.lsaparser.entity.UserEntity;
import com.sava.lsaparser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        List<UserEntity> userEntities =  userRepository.findAll();
        return null;
    }
}
