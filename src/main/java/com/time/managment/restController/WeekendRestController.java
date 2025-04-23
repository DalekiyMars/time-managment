package com.time.managment.restController;

import com.time.managment.constants.Constants;
import com.time.managment.dto.WeekendDTO;
import com.time.managment.dto.WeekendToDelete;
import com.time.managment.entity.Weekend;
import com.time.managment.service.WeekendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekends")
@RequiredArgsConstructor
public class WeekendRestController {
    private final WeekendService weekendService;

    // Получить список выходных по timesheet
    @GetMapping("/{timeSheet}")
    public ResponseEntity<List<WeekendDTO>> getWeekends(@PathVariable Integer timeSheet) {
        final List<WeekendDTO> weekends = weekendService.getWeekendsByTimesheet(timeSheet);
        return ResponseEntity.ok(weekends);
    }

    // Сохранить выходной день
    @PostMapping("/add")
    public ResponseEntity<WeekendDTO> addWeekend(@RequestBody Weekend weekend) {
        final WeekendDTO savedWeekend = weekendService.saveWeekend(weekend);
        return ResponseEntity.ok(savedWeekend);
    }

    // Удалить выходной день
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWeekend(@RequestBody WeekendToDelete weekend) {
        weekendService.deleteWeekend(weekend);
        return ResponseEntity.ok(Constants.ClassicMessages.INFO_DELETED_SUCCESSFULLY);
    }
}