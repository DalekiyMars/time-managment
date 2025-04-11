package com.time.managment.service;

import com.time.managment.dto.DayRecordDto;
import com.time.managment.entity.Presence;
import com.time.managment.entity.Weekend;
import com.time.managment.repository.PresenceRepository;
import com.time.managment.repository.WeekendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final PresenceRepository presenceRepository;
    private final WeekendRepository weekendRepository;

    public List<DayRecordDto> getRecordsForWeek(Integer timeSheet) {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(7);
        return mergePresenceAndWeekend(timeSheet, start, today);
    }

    public List<DayRecordDto> getRecordsForMonth(Integer timeSheet) {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        return mergePresenceAndWeekend(timeSheet, start, today);
    }

    public Map<Integer, Long> getWeekendCountLast6Months(Integer timeSheet) {
        LocalDate start = LocalDate.now().minusMonths(6);
        List<Object[]> result = weekendRepository.countByMonth(timeSheet, start);

        return result.stream().collect(Collectors.toMap(
                row -> ((Double) row[0]).intValue(),              // месяц
                row -> ((BigInteger) row[1]).longValue()          // количество
        ));
    }

    private List<DayRecordDto> mergePresenceAndWeekend(Integer timeSheet, LocalDate start, LocalDate end) {
        List<Presence> presences = presenceRepository.findByUserTimeSheet_TimeSheetAndTimeMarkBetween(
                timeSheet,
                start.atStartOfDay(),
                end.plusDays(1).atStartOfDay()
        );

        List<Weekend> weekends = weekendRepository.findByUserTimeSheetAndWeekendDateBetween(
                timeSheet,
                start,
                end
        );

        Map<LocalDate, Presence> presenceMap = presences.stream()
                .collect(Collectors.toMap(p -> p.getTimeMark().toLocalDate(), Function.identity(), (a, b) -> a));

        Map<LocalDate, Weekend> weekendMap = weekends.stream()
                .collect(Collectors.toMap(Weekend::getWeekendDate, Function.identity(), (a, b) -> a));

        List<DayRecordDto> result = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (presenceMap.containsKey(date)) {
                Presence p = presenceMap.get(date);
                result.add(new DayRecordDto(date, "presence", p.getTimeMark(), null));
            } else if (weekendMap.containsKey(date)) {
                Weekend w = weekendMap.get(date);
                result.add(new DayRecordDto(date, "weekend", null, w.getReason().toString()));
            }
        }

        return result;
    }
}
