package com.time.managment.controller;

import com.time.managment.dto.PresenceDTO;
import com.time.managment.entity.Presence;
import com.time.managment.service.PresenceService;
import com.time.managment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/presence")
@RequiredArgsConstructor
public class PresenceController {
    private final PresenceService presenceService;
    private final UserService userService;

    @GetMapping("/search")
    public String searchPresence(@RequestParam(value = "timeSheet",required = false) Integer timeSheet, Model model) {
        if (timeSheet == null)
            return "presence-search";
        try {
            List<PresenceDTO> presences = presenceService.getPresences(timeSheet);
            model.addAttribute("presences", presences);
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", "Сотрудник с таким табельным номером не найден.");
        }
        return "presence-search";
    }

    @GetMapping("/add-form")
    public String saveNew(){
        return "presence-add";
    }

    @PostMapping("/add-form")
    public String savePresence(@RequestParam Integer timeSheet,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeMark,
                               Model model) {
        try {
            Presence presence = new Presence();
            presence.setTimeMark(timeMark);
            presence.setUserTimeSheet(userService.getUser(timeSheet));
            PresenceDTO saved = presenceService.savePresence(presence);
            model.addAttribute("message", "Сохранено: " + saved.getUser().getUsername() + " — " + saved.getTimeMark());
            model.addAttribute("success", true);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка: " + ex.getMessage());
            model.addAttribute("success", false);
        }
        return "presence-add";
    }

    @GetMapping("/all")
    public String getAllPresences(Model model) {
        List<Presence> presences = presenceService.getAllPresence();
        model.addAttribute("presences", presences);
        return "presence-list"; // имя шаблона
    }
}
