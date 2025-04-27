package com.time.managment.restController;

import com.time.managment.dto.PresenceDTO;
import com.time.managment.entity.Presence;
import com.time.managment.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presences")
@RequiredArgsConstructor
public class PresenceRestController {
    private final PresenceService presenceService;

    // Получить все присутствия для конкретного timesheet
    @GetMapping("/{timeSheet}")
    public ResponseEntity<List<PresenceDTO>> getPresencesByTimeSheet(@PathVariable Integer timeSheet) {
        final List<PresenceDTO> presences = presenceService.getPresences(timeSheet);
        return ResponseEntity.ok(presences);
    }

    // Сохранить присутствие
    @PostMapping("/add")
    public ResponseEntity<PresenceDTO> savePresence(@RequestBody Presence presence) {
        final PresenceDTO saved = presenceService.savePresenceForREST(presence);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Получить все присутствия (без фильтрации) - для администраторов
    @GetMapping
    public ResponseEntity<List<Presence>> getAllPresences() {
        final List<Presence> presences = presenceService.getAllPresence();
        return ResponseEntity.ok(presences);
    }
}
