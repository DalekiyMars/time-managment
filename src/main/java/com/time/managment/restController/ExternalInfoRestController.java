package com.time.managment.restController;

import com.time.managment.constants.Constants;
import com.time.managment.dto.PresenceDTO;
import com.time.managment.dto.WeekendDTO;
import com.time.managment.entity.Weekend;
import com.time.managment.service.PresenceService;
import com.time.managment.service.WeekendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
@RestController
@RequestMapping("/api/info-controller")
@RequiredArgsConstructor
public class ExternalInfoRestController {
    private final PresenceService presenceService;
    private final WeekendService weekendService;

    @PostMapping("/process-weekend-data")
    public ResponseEntity<?> processWeekendData(@RequestBody String weekendData) {
        try {
            final Weekend weekend = weekendService.parseWeekendString(weekendData);
            final WeekendDTO saved = weekendService.saveWeekendForREST(weekend);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
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
