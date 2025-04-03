package mapper;

import dto.WeekendDTO;
import entity.Weekend;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeekendMapper {
    WeekendDTO toWeekendDTO(Weekend weekend);
}
