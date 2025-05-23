package com.time.managment.controller;

import com.time.managment.constants.AbsenceReason;
import com.time.managment.constants.Constants;
import com.time.managment.dto.HandlerDto;
import com.time.managment.dto.WeekendDTO;
import com.time.managment.dto.WeekendToDelete;
import com.time.managment.entity.Weekend;
import com.time.managment.service.WeekendService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/weekends")
@RequiredArgsConstructor
public class WeekendController {
    private final WeekendService weekendService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER')")
    @GetMapping("/search-form")
    public String searchWeekendsForm(){
        return "weekends-list";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timesheet)) " +
            "or (hasRole('USER') and @accessService.isSelf(#timesheet))")
    @GetMapping("/search")
    public String searchWeekends(@RequestParam(value = "timeSheet") Integer timesheet,
                                 Model model) {
        try {
            final List<WeekendDTO> weekends = weekendService.getWeekendsByTimesheet(timesheet);
            model.addAttribute(Constants.ModelValues.WEEKENDS, weekends);
        } catch (NoSuchElementException e) {
            model.addAttribute(Constants.ModelValues.ERROR_MESSAGE, "Сотрудник с таким табельным номером не найден.");
        }

        return "weekends-list";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/add-form")
    public String showAddWeekendForm() {
        return "weekend-add";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#userTimeSheet))")
    @PostMapping("/add-form")
    public String saveWeekend(@RequestParam Integer userTimeSheet,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekendDate,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
                              @RequestParam String reason,
                              Model model) {
        final var parsedReason = AbsenceReason.fromString(reason);
        final var weekend = new Weekend(userTimeSheet, parsedReason, weekendDate, startTime, endTime);

        setModelAttributes(model, weekendService.saveWeekendForView(weekend));

        return "weekend-add";
    }

    private void setModelAttributes(Model model, HandlerDto result){
        model.addAttribute(Constants.ModelValues.MESSAGE, result.getMessage());
        model.addAttribute(Constants.ModelValues.SUCCESS, result.getSuccess());
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/delete-form")
    public String showDeleteWeekendForm() {
        return "weekend-delete";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet))")
    @PostMapping("/delete")
    public String deleteWeekend(@RequestParam Integer timeSheet,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekendDate,
                                Model model) {
        final var toDelete = new WeekendToDelete(timeSheet, String.valueOf(weekendDate));
        setModelAttributes(model, weekendService.deleteWeekendForView(toDelete));

        return "weekend-delete";
    }
}