package com.time.managment.controller;

import com.time.managment.dto.PresenceDTO;
import com.time.managment.entity.Presence;
import com.time.managment.service.PresenceService;
import com.time.managment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequiredArgsConstructor
@RequestMapping("/presences")
public class PresenceController {
    private final PresenceService presenceService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER')")
    @GetMapping("/search")
    public String showSearchForm() {
        return "presences-search";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet)) " +
            "or (hasRole('USER') and @accessService.isSelf(#timeSheet))")
    @GetMapping(value = "/search", params = "timeSheet")
    public String searchPresence(@RequestParam("timeSheet") Integer timeSheet, Model model) {
        try {
            List<PresenceDTO> presences = presenceService.getPresences(timeSheet);
            model.addAttribute("presences", presences);
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", "Сотрудник с таким табельным номером не найден.");
        }

        return "presences-search";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/add-form")
    public String saveNew() {
        return "presence-add";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet))")
    @PostMapping("/add-form")
    public String savePresence(@RequestParam Integer timeSheet,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeMark,
                               Model model) {
        try {
            final Presence presence = new Presence()
                    .setTimeMark(timeMark)
                    .setUserTimeSheet(userService.getUser(timeSheet));

            final PresenceDTO saved = presenceService.savePresence(presence);
            model.addAttribute("message", "Сохранено: " + saved.getUser().getUsername() + " — " + saved.getTimeMark());
            model.addAttribute("success", true);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка: " + ex.getMessage());
            model.addAttribute("success", false);
        }
        return "presence-add";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public String getAllPresences(Model model) {
        final List<Presence> presences = presenceService.getAllPresence();
        model.addAttribute("presences", presences);
        return "presences-list";
    }
}
