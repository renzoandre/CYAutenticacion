package com.bootcamp.api.mapper;

import com.bootcamp.api.dto.CreateUserDto;
import com.bootcamp.api.dto.UpdateUserDto;
import com.bootcamp.api.dto.UserDto;
import com.bootcamp.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto toResponse(User user);
    UserDto toResponseList(User users);
    User toModel(CreateUserDto createUserDto);
    User toModel(UpdateUserDto updateUserDto);
}
