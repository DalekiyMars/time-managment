package com.time.managment.repository.mapper;

import com.time.managment.dto.UserDTO;
import com.time.managment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDTO toUserDto(User user);
}
