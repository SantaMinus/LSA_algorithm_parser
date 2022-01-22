package com.sava.lsaparser.mapper;

import com.sava.lsaparser.dto.UserDto;
import com.sava.lsaparser.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(UserEntity entity);

    List<UserDto> entityListToDtoList(List<UserEntity> entityList);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserEntity dtoToEntity(UserDto dto);

    List<UserEntity> dtoListToEntityList(List<UserDto> userDtoList);
}
