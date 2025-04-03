package mapper;

import dto.DepartmentDTO;
import entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
    DepartmentDTO toDepartmentDTO(Department department);
}
