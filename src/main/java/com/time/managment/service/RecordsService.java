package com.time.managment.service;

import com.time.managment.dto.CombinedRecordDTO;
import com.time.managment.entity.Presence;
import com.time.managment.entity.Weekend;
import com.time.managment.repository.PresenceRepository;
import com.time.managment.repository.WeekendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecordsService {
    private final PresenceRepository presenceRepository;
    private final WeekendRepository weekendRepository;

    public List<CombinedRecordDTO> getCombinedRecords(Integer userId, LocalDate periodStart, LocalDate periodEnd) {
        LocalDateTime presenceStart = periodStart.atStartOfDay();
        LocalDateTime presenceEnd = periodEnd.plusDays(1).atStartOfDay();

        List<Presence> presences = presenceRepository.findByUserIdAndTimeMarkInRange(userId, presenceStart, presenceEnd);
        List<Weekend> weekends = weekendRepository.findByUserTimeSheetAndWeekendDateBetween(userId, periodStart, periodEnd);

        List<CombinedRecordDTO> combined = new ArrayList<>();

        // Обработка Presence
        for (Presence p : presences) {
            CombinedRecordDTO dto = getCombinedRecordDTO(p);
            combined.add(dto);
        }

        // Обработка Weekend
        for (Weekend w : weekends) {
            CombinedRecordDTO dto = getCombinedRecordDTO(w);
            combined.add(dto);
        }

        combined.sort(Comparator.comparing(
                CombinedRecordDTO::getDate,
                Comparator.nullsLast(Comparator.reverseOrder())
        ));
        return combined;
    }

    private static CombinedRecordDTO getCombinedRecordDTO(Presence p) {
        CombinedRecordDTO dto = new CombinedRecordDTO();
        dto.setType("presence");
        dto.setUserId(p.getUserTimeSheet().getTimeSheet());
        // Устанавливаем дату и время для Presence
        LocalDateTime timeMark = p.getTimeMark();
        dto.setDate(timeMark.toLocalDate()); // Дата присутствия
        dto.setStartTime(timeMark.toLocalTime()); // Время начала присутствия
        dto.setEndTime(timeMark.toLocalTime()); // Время окончания присутствия (равно времени начала)
        dto.setReason(null); // Для присутствия причина не используется
        return dto;
    }

    private static CombinedRecordDTO getCombinedRecordDTO(Weekend w) {
        CombinedRecordDTO dto = new CombinedRecordDTO();
        dto.setType("weekend");
        dto.setUserId(w.getUserTimeSheet());

        // Устанавливаем дату
        dto.setDate(w.getWeekendDate()); // Устанавливаем только дату выходного

        // Проверяем, если выходной на весь день (startTime и endTime равны null)
        if (Objects.isNull(w.getStartTime()) && Objects.isNull(w.getEndTime())) {
            // Если это выходной на весь день, то время не задаём
            dto.setStartTime(null);
            dto.setEndTime(null);
        } else {
            // Если время есть, то устанавливаем время начала и окончания
            dto.setStartTime(w.getStartTime()); // Теперь только LocalTime
            dto.setEndTime(w.getEndTime());     // Теперь только LocalTime
        }

        // Устанавливаем причину (если есть)
        dto.setReason(w.getReason());
        return dto;
    }

}