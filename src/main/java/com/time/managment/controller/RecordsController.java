package com.time.managment.controller;

import com.time.managment.constants.Constants;
import com.time.managment.dto.CombinedRecordDTO;
import com.time.managment.service.RecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordsController {
    private final RecordsService recordsService;
    @PreAuthorize("hasAnyRole('MANAGER', 'USER', 'ADMIN')")
    @GetMapping("/form")
    public String showCombinedRecords() {
        return "records";
    }
    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet)) " +
            "or (hasRole('USER') and @accessService.isSelf(#timeSheet))")    @GetMapping
    public String getRecords(
            @RequestParam(value = "period", defaultValue = "week") String period,
            @RequestParam(value = "timeSheet", required = false) Integer timeSheet,
            Model model) {
        if (Objects.isNull(timeSheet)) {
            model.addAttribute(Constants.ModelValues.RECORDS, Collections.emptyList());
            model.addAttribute(Constants.ModelValues.ERROR_MESSAGE, "Пожалуйста, введите ID пользователя.");
            return "records";
        }
        final LocalDate now = LocalDate.now();
        final LocalDate start = switch (period) {
            case "week" -> now.minusDays(6);
            case "month" -> now.minusMonths(1);
            case "year" -> now.minusYears(1);
            default -> throw new IllegalArgumentException("Invalid period");
        };

        final List<CombinedRecordDTO> records = recordsService.getCombinedRecords(timeSheet, start, now);
        model.addAttribute(Constants.ModelValues.RECORDS, records);
        model.addAttribute(Constants.ModelValues.USER_ID, timeSheet);
        model.addAttribute(Constants.ModelValues.PERIOD_DISPLAY, getPeriodDisplay(period));
        return "records";
    }

    private String getPeriodDisplay(String period) {
        if (Objects.isNull(period)) return "Unknown";
        return switch (period) {
            case "week" -> "Last Week";
            case "month" -> "Last Month";
            case "year" -> "Last Year";
            default -> "Unknown";
        };
    }
}