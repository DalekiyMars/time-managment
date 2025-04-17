package com.time.managment.controller;

import com.time.managment.constants.AbsenceReason;
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
@RequiredArgsConstructor
public class WeekendController {

    private final WeekendService weekendService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER')")
    @GetMapping("/search")
    public String searchWeekends(@RequestParam(value = "timeSheet", required = false) Integer timeSheet,
                                 Model model) {
        if (timeSheet != null) {
            try {
                List<WeekendDTO> weekends = weekendService.getWeekendsByTimesheet(timeSheet);
                model.addAttribute("weekends", weekends);
            } catch (NoSuchElementException e) {
                model.addAttribute("errorMessage", "Сотрудник с таким табельным номером не найден.");
            }
        }
        return "weekends-list";
    }

    //FIXME пиздец падаем при попытке
    //FIXME добавить проверку что пользователь принадлежит департаменту что и менеджер который его запрашивает
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/add-form")
    public String showAddWeekendForm() {
        return "weekend-add";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/add-form")
    public String saveWeekend(@RequestParam Integer userTimeSheet,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekendDate,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
                              @RequestParam String reason,
                              Model model) {
        try {
            AbsenceReason parsedReason = AbsenceReason.fromString(reason);

            // Проверка на наличие такой же записи
            boolean exists = weekendService.existsByFields(userTimeSheet, weekendDate, startTime, endTime, parsedReason);

            if (exists) {
                model.addAttribute("message", "Ошибка: такая запись уже существует.");
                model.addAttribute("success", false);
            } else {
                weekendService.saveWeekend(new Weekend(userTimeSheet, parsedReason, weekendDate, startTime, endTime));
                model.addAttribute("message", "Выходной успешно добавлен.");
                model.addAttribute("success", true);
            }
        } catch (NoSuchElementException e) {
            model.addAttribute("message", "Сотрудник с таким табельным номером не найден.");
            model.addAttribute("success", false);
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка сохранения. Проверьте данные и повторите попытку.");
            model.addAttribute("success", false);
        }

        return "weekend-add";
    }


    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/delete-form")
    public String showDeleteWeekendForm() {
        return "weekend-delete";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/delete")
    public String deleteWeekend(@RequestParam Integer timeSheet,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekendDate,
                                Model model) {
        try {
            WeekendToDelete toDelete = new WeekendToDelete(timeSheet, String.valueOf(weekendDate));
            weekendService.deleteWeekend(toDelete);
            model.addAttribute("message", "Выходной успешно удалён.");
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка при удалении: " + e.getMessage());
            model.addAttribute("success", false);
        }
        return "weekend-delete"; // возвращаем форму удаления
    }
}