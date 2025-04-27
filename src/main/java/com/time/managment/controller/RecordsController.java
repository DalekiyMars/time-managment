package com.time.managment.controller;

import com.time.managment.constants.Constants;
import com.time.managment.dto.CombinedRecordDTO;
import com.time.managment.service.RecordsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
            "or (hasRole('USER') and @accessService.isSelf(#timeSheet))")
    @GetMapping
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

    @GetMapping("/download")
    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet)) " +
            "or (hasRole('USER') and @accessService.isSelf(#timeSheet))")
    public ResponseEntity<byte[]> downloadCsv(
            @RequestParam(value = "period", defaultValue = "week") String period,
            @RequestParam(value = "timeSheet") Integer timeSheet) throws IOException {

        if (timeSheet == null) {
            return ResponseEntity.badRequest().build();
        }

        LocalDate now = LocalDate.now();
        LocalDate start = switch (period) {
            case "week" -> now.minusDays(6);
            case "month" -> now.minusMonths(1);
            case "year" -> now.minusYears(1);
            default -> throw new IllegalArgumentException("Invalid period: " + period);
        };

        List<CombinedRecordDTO> records = recordsService.getCombinedRecords(timeSheet, start, now);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Пишем BOM для UTF-8
        byteArrayOutputStream.write(0xEF);
        byteArrayOutputStream.write(0xBB);
        byteArrayOutputStream.write(0xBF);

        OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);

        // Настраиваем CSVPrinter с разделителем ";"
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader("Пользователь ID", "Дата", "Тип", "Начало", "Окончание", "Причина").get();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {
            for (CombinedRecordDTO record : records) {
                csvPrinter.printRecord(
                        record.getTimeSheet(),
                        record.getDate(),
                        record.getReadableType(),
                        record.getStartTime() != null ? record.getStartTime() : "Весь день",
                        record.getEndTime() != null ? record.getEndTime() : "Весь день",
                        record.getReason()
                );
            }
        }

        byte[] csvBytes = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + timeSheet + "-records.csv");
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(csvBytes);
    }
}