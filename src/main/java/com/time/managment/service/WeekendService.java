package com.time.managment.service;

import com.time.managment.constants.AbsenceReason;
import com.time.managment.dto.WeekendDTO;
import com.time.managment.dto.WeekendToDelete;
import com.time.managment.entity.Weekend;
import com.time.managment.mapper.WeekendMapper;
import com.time.managment.repository.WeekendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
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
                .sorted(Comparator.comparing(WeekendDTO::getWeekendDate).reversed())
                .collect(Collectors.toList());
    }

    public WeekendDTO saveWeekend(Weekend weekend) {
        Weekend savedWeekend = weekendRepository.save(weekend);
        return weekendMapper.toWeekendDTO(savedWeekend);
    }

    @Transactional
    public void deleteWeekend(WeekendToDelete dto) {
        Weekend weekend = weekendRepository.findByUserTimeSheetAndWeekendDate(dto.getTimeSheet(), dto.getWeekendDate())
                .orElseThrow(() -> new NoSuchElementException("Такой выходной не найден."));
        weekendRepository.delete(weekend);
    }

    public boolean existsByFields(Integer userTimeSheet, LocalDate weekendDate,
                                  LocalTime startTime, LocalTime endTime, AbsenceReason reason) {
        return weekendRepository.existsByUserTimeSheetAndWeekendDateAndStartTimeAndEndTimeAndReason(
                userTimeSheet, weekendDate, startTime, endTime, reason
        );
    }
}
