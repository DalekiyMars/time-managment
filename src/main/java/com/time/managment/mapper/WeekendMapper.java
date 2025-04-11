package com.time.managment.mapper;

import com.time.managment.dto.WeekendDTO;
import com.time.managment.entity.User;
import com.time.managment.entity.Weekend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeekendMapper {
    // Маппинг для преобразования сущности Weekend в DTO
    @Mapping(target = "userTimeSheet", source = "userTimeSheet")  // просто указываем на userTimeSheet, без timeSheet
    WeekendDTO toWeekendDTO(Weekend weekend);

    @Mapping(target = "userTimeSheet", source = "userTimeSheet") // Простое маппирование userTimeSheet
    Weekend toWeekend(WeekendDTO weekendDTO);
}
