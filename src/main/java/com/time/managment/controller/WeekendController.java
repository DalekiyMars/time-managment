package com.time.managment.controller;

import com.time.managment.dto.WeekendDTO;
import com.time.managment.dto.WeekendToDelete;
import com.time.managment.entity.Weekend;
import com.time.managment.service.WeekendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weekends")
@RequiredArgsConstructor
public class WeekendController {
    private final WeekendService weekendService;

    // Получить список выходных по timesheet
    @GetMapping("/{timeSheet}")
    public ResponseEntity<List<WeekendDTO>> getWeekends(@PathVariable Integer timeSheet) {
        List<WeekendDTO> weekends = weekendService.getWeekendsByTimesheet(timeSheet);
        return ResponseEntity.ok(weekends);
    }

    // Сохранить выходной день
    @PostMapping("/add")
    public ResponseEntity<WeekendDTO> addWeekend(@RequestBody WeekendDTO weekendDTO) {
        WeekendDTO savedWeekend = weekendService.saveWeekend(weekendDTO);
        return ResponseEntity.ok(savedWeekend);
    }

    // Удалить выходной день
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWeekend(@RequestBody WeekendToDelete weekend) {
        weekendService.deleteWeekend(weekend);
        return ResponseEntity.ok("Weekend deleted successfully");
    }
}