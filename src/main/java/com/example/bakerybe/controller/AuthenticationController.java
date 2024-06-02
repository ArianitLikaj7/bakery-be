package com.example.bakerybe.controller;

import com.example.bakerybe.dto.*;
import com.example.bakerybe.service.AuthenticationService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @NotNull AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody @NotNull RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authenticationService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

    @GetMapping("/auth-me")
    public CurrentLoggedIn getLoggedInUser(){
        return authenticationService.getCurrentLoggedInUser();
    }
}
