package com.time.managment.controller;

import com.time.managment.dto.CombinedRecordDTO;
import com.time.managment.service.RecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordsController {
    private final RecordsService recordsService;

    @GetMapping("/form")
    public String showCombinedRecords() {
        return "records";
    }

    @GetMapping
    public String getRecords(
            @RequestParam(value = "period", defaultValue = "week") String period,
            @RequestParam(value = "userId", required = false) Integer userId,
            Model model) {
        if (userId == null) {
            model.addAttribute("records", Collections.emptyList());
            model.addAttribute("errorMessage", "Пожалуйста, введите ID пользователя.");
            return "records";
        }
        LocalDate now = LocalDate.now();
        LocalDate start = switch (period) {
            case "week" -> now.minusDays(6);
            case "month" -> now.minusMonths(1);
            case "year" -> now.minusYears(1);
            default -> throw new IllegalArgumentException("Invalid period");
        };

        List<CombinedRecordDTO> records = recordsService.getCombinedRecords(userId, start, now);
        model.addAttribute("records", records);
        model.addAttribute("userId", userId);
        model.addAttribute("periodDisplay", getPeriodDisplay(period));
        return "records";
    }

    private String getPeriodDisplay(String period) {
        if (period == null) return "Unknown";
        return switch (period) {
            case "week" -> "Last Week";
            case "month" -> "Last Month";
            case "year" -> "Last Year";
            default -> "Unknown";
        };
    }
}