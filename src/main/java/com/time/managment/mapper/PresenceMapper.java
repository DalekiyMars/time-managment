package com.time.managment.mapper;

import com.time.managment.dto.PresenceDTO;
import com.time.managment.entity.Presence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = UserMapper.class)
public interface PresenceMapper {
    @Mapping(source = "userTimeSheet", target = "user") // Указываем связь между userId и user
    PresenceDTO toPresenceDTO(Presence presence);
}
