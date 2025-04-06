package com.time.managment.repository.mapper;

import com.time.managment.dto.WeekendDTO;
import com.time.managment.entity.Weekend;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeekendMapper {
    //@Mapping(source = "userId", target = "user")
    WeekendDTO toWeekendDTO(Weekend weekend);
}
