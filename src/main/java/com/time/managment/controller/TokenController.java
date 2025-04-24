package com.time.managment.controller;

import com.time.managment.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal-auth")
public class TokenController {
    private final JwtUtil jwtUtil;

//    @PostMapping("/generate")
//    public ResponseEntity<String> generateToken(@RequestParam String subject) {
//        String token = jwtUtil.generateToken(subject);
//        log.info("Generated JWT for [{}]: {}", subject, token);
//        return ResponseEntity.ok(token);
//    }
}
