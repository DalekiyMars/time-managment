package com.time.managment.service;

import com.time.managment.dto.WeekendDTO;
import com.time.managment.dto.WeekendToDelete;
import com.time.managment.entity.Weekend;
import com.time.managment.mapper.WeekendMapper;
import com.time.managment.repository.WeekendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeekendService {
    private final WeekendMapper weekendMapper;
    private final WeekendRepository weekendRepository;

    public List<WeekendDTO> getWeekendsByTimesheet(Integer timesheet) {
        return weekendRepository.findByUserTimeSheet(timesheet).stream()
                .map(weekendMapper::toWeekendDTO)
                .collect(Collectors.toList());
    }

    public WeekendDTO saveWeekend(WeekendDTO weekendDTO) {
        Weekend weekend = weekendMapper.toWeekend(weekendDTO);

        Weekend savedWeekend = weekendRepository.save(weekend);
        return weekendMapper.toWeekendDTO(savedWeekend);
    }

    public void deleteWeekend(WeekendToDelete weekend) {
        weekendRepository.deleteByUserTimeSheetAndWeekendDate(weekend.getTimeSheet(), weekend.getWeekendDate());
    }

    public List<Weekend> getWeekendsByUserTimeSheetAndDateRange(Integer userTimeSheet, LocalDate startDate, LocalDate endDate) {
        return weekendRepository.findByUserTimeSheetAndWeekendDateBetween(userTimeSheet, startDate, endDate);
    }
}
