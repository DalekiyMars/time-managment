package com.time.managment.service;

import com.time.managment.constants.AbsenceReason;
import com.time.managment.dto.HandlerDto;
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
import java.time.format.DateTimeFormatter;
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

    public WeekendDTO saveWeekendForREST(Weekend weekend) {
        final Weekend savedWeekend = weekendRepository.save(weekend);
        return weekendMapper.toWeekendDTO(savedWeekend);
    }

    public HandlerDto saveWeekendForView(Weekend weekend) {
        try {
            final Integer timeSheet = weekend.getUserTimeSheet();
            final LocalDate date = weekend.getWeekendDate();
            final LocalTime start = weekend.getStartTime();
            final LocalTime end = weekend.getEndTime();
            final AbsenceReason reason = weekend.getReason();

            if (existsByFields(timeSheet, date, start, end, reason)) {
                return new HandlerDto(false, "Ошибка: такая запись уже существует.");
            }

            Weekend saved = weekendRepository.save(weekend);
            return new HandlerDto(true, "Выходной успешно добавлен: " + saved.getWeekendDate());
        } catch (NoSuchElementException e) {
            return new HandlerDto(false, "Сотрудник с таким табельным номером не найден.");
        } catch (Exception e) {
            return new HandlerDto(false, "Ошибка сохранения. Проверьте данные и повторите попытку.");
        }
    }

    @Transactional
    public void deleteWeekendForREST(WeekendToDelete dto) {
        final Weekend weekend = weekendRepository.findByUserTimeSheetAndWeekendDate(dto.getTimeSheet(), dto.getWeekendDate())
                .orElseThrow(() -> new NoSuchElementException("Такой выходной не найден."));
        weekendRepository.delete(weekend);
    }

    public HandlerDto deleteWeekendForView(WeekendToDelete dto) {
        try {
            Weekend weekend = weekendRepository.findByUserTimeSheetAndWeekendDate(dto.getTimeSheet(), dto.getWeekendDate())
                    .orElseThrow(() -> new NoSuchElementException("Такой выходной не найден."));
            weekendRepository.delete(weekend);
            return new HandlerDto(true, "Выходной успешно удалён: " + dto.getWeekendDate());
        } catch (NoSuchElementException e) {
            return new HandlerDto(false, "Ошибка: " + e.getMessage());
        } catch (Exception e) {
            return new HandlerDto(false, "Не удалось удалить выходной. Повторите попытку.");
        }
    }

    public boolean existsByFields(Integer userTimeSheet, LocalDate weekendDate,
                                  LocalTime startTime, LocalTime endTime, AbsenceReason reason) {
        return weekendRepository.existsByUserTimeSheetAndWeekendDateAndStartTimeAndEndTimeAndReason(
                userTimeSheet, weekendDate, startTime, endTime, reason
        );
    }

    public Weekend parseWeekendString(String input) {
        try {
            String[] parts = input.split(";");
            if (parts.length != 7) {
                throw new IllegalArgumentException("Неверный формат строки: ожидается 7 частей.");
            }

            Integer timesheet = Integer.parseInt(parts[1]);
            LocalDate date = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            AbsenceReason reason = AbsenceReason.fromString(parts[4]);

            LocalTime start = "null".equalsIgnoreCase(parts[5]) ? null : LocalTime.parse(parts[5]);
            LocalTime end = "null".equalsIgnoreCase(parts[6]) ? null : LocalTime.parse(parts[6]);

            return new Weekend(timesheet, reason, date, start, end);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при разборе строки увольнительной: " + e.getMessage(), e);
        }
    }

}
