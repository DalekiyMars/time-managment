package com.time.managment.mapper;

import com.time.managment.dto.DepartmentDTO;
import com.time.managment.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
    @Mapping(source = "userTimeSheet", target = "user") // Указываем связь между userId и user
    DepartmentDTO toDepartmentDTO(Department department);
}
