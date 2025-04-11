package com.time.managment.controller;

import com.time.managment.dto.DayRecordDto;
import com.time.managment.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-records")
public class FilteredRecordsController {
    private final FilterService filterService;

    // Получить все записи за текущую неделю
    @GetMapping("/week/{timeSheet}")
    public ResponseEntity<List<DayRecordDto>> getRecordsForWeek(@PathVariable Integer timeSheet) {
        List<DayRecordDto> result = filterService.getRecordsForWeek(timeSheet);
        return ResponseEntity.ok(result);
    }

    // Получить все записи за текущий месяц
    @GetMapping("/month/{timeSheet}")
    public ResponseEntity<List<DayRecordDto>> getRecordsForMonth(@PathVariable Integer timeSheet) {
        List<DayRecordDto> result = filterService.getRecordsForMonth(timeSheet);
        return ResponseEntity.ok(result);
    }

    // Получить статистику по выходным дням за последние 6 месяцев
    @GetMapping("/weekends-stats/{timeSheet}")
    public ResponseEntity<Map<Integer, Long>> getWeekendStatsLast6Months(@PathVariable Integer timeSheet) {
        Map<Integer, Long> result = filterService.getWeekendCountLast6Months(timeSheet);
        return ResponseEntity.ok(result);
    }
}
