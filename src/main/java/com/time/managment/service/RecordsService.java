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
        final LocalDateTime presenceStart = periodStart.atStartOfDay();
        final LocalDateTime presenceEnd = periodEnd.plusDays(1).atStartOfDay();

        final List<Presence> presences = presenceRepository.findByUserIdAndTimeMarkInRange(userId, presenceStart, presenceEnd);
        final List<Weekend> weekends = weekendRepository.findByUserTimeSheetAndWeekendDateBetween(userId, periodStart, periodEnd);

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
        final LocalDateTime timeMark = p.getTimeMark();

        return new CombinedRecordDTO()
                .setType("presence")
                .setTimeSheet(p.getUserTimeSheet().getTimeSheet())
                // Устанавливаем дату и время для Presence
                .setDate(timeMark.toLocalDate())
                .setStartTime(timeMark.toLocalTime())
                .setEndTime(timeMark.toLocalTime())
                .setReason(null);
    }


    private static CombinedRecordDTO getCombinedRecordDTO(Weekend w) {
        CombinedRecordDTO dto = new CombinedRecordDTO()
                .setType("weekend")
                .setTimeSheet(w.getUserTimeSheet())
                .setDate(w.getWeekendDate())
                .setStartTime(w.getStartTime())
                .setEndTime(w.getEndTime());

        // Устанавливаем причину (если есть)
        dto.setReason(w.getReason());
        return dto;
    }

}