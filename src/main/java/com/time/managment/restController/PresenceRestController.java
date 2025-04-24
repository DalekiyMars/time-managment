package com.time.managment.restController;

import com.time.managment.constants.Constants;
import com.time.managment.dto.PresenceDTO;
import com.time.managment.entity.Presence;
import com.time.managment.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
        final PresenceDTO saved = presenceService.savePresence(presence);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Получить все присутствия (без фильтрации) - для администраторов
    @GetMapping
    public ResponseEntity<List<Presence>> getAllPresences() {
        final List<Presence> presences = presenceService.getAllPresence();
        return ResponseEntity.ok(presences);
    }

    //FIXME сделать чтобы только сервер мог посылать этот запрос
    @PostMapping("/process-scud-data")
    public ResponseEntity<?> processScudData(@RequestBody String scudData) {
        try {
            final PresenceDTO presenceDTO = presenceService.processPresenceString(scudData);
            return ResponseEntity.status(HttpStatus.CREATED).body(presenceDTO);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.ExceptionMessages.SOMETHING_WENT_WRONG);
        }
    }
}
