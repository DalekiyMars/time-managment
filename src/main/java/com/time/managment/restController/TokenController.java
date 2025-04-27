package com.time.managment.restController;

import com.time.managment.dto.AuthRequest;
import com.time.managment.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal-auth")
public class TokenController {

    private final JwtService jwtService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest subject) {
        String token = jwtService.serviceAuthorized(subject.getServiceName());
        log.info("Generated JWT for [{}]: {}", subject, token);
        return ResponseEntity.ok(token);
    }
}
