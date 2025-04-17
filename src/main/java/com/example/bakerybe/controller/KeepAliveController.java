package com.example.bakerybe.controller;

import com.example.bakerybe.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeepAliveController {

    private final UserRepository userRepository;

    @GetMapping("/internal/keepalive")
    public ResponseEntity<String> keepAliveWithDbPing() {
        try {
            boolean dbOk = userRepository.count() >= 0;
            return ResponseEntity.ok(dbOk ? "alive+db" : "db empty");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("DB error: " + e.getMessage());
        }
    }
}
