package com.sava.lsaparser.mapper;

import com.sava.lsaparser.dto.UserDto;
import com.sava.lsaparser.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User entity);

    List<UserDto> entityListToDtoList(List<User> entityList);

    @Mapping(target = "id", ignore = true)
    User dtoToEntity(UserDto dto);

    List<User> dtoListToEntityList(List<UserDto> userDtoList);
}
