package com.example.bakerybe.service;

import com.example.bakerybe.config.JwtService;
import com.example.bakerybe.dto.AuthenticationRequest;
import com.example.bakerybe.dto.AuthenticationResponse;
import com.example.bakerybe.dto.CurrentLoggedInUserDto;
import com.example.bakerybe.dto.RefreshTokenRequest;
import com.example.bakerybe.entity.CustomUser;
import com.example.bakerybe.entity.User;
import com.example.bakerybe.exception.TokenRefreshException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomUserDetailService customUserDetailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        var user = customUserDetailService.loadUserByUsername(request.username());
        return new AuthenticationResponse(jwtService.generateToken(user),
                jwtService.generateRefreshToken(user),user.getRole(), user.getHasBranches());
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request){
        String requestRefreshToken = request.token();

        User user = customUserDetailService.
                loadUserByUsername(jwtService.extractUsername(requestRefreshToken));

        if (!jwtService.isTokenValid(requestRefreshToken, user)){
            throw new TokenRefreshException("Refresh token was expired. Please make a new sign-in request");
        }

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token, requestRefreshToken,user.getRole(), user.getHasBranches());
    }


    public CurrentLoggedInUserDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = (User) authentication.getPrincipal();
        CustomUser loggedCustomUser = (CustomUser) authentication.getPrincipal();

        Long bakeryId = null;
        if (loggedCustomUser instanceof CustomUser) {
            bakeryId = loggedCustomUser.getBakeryId();
        }

        return CurrentLoggedInUserDto.builder()
                .userId(loggedUser.getId())
                .firstName(loggedUser.getFirstName())
                .lastName(loggedUser.getLastName())
                .role(loggedUser.getRole().name())
                .email(loggedUser.getUsername())
                .hasBranches(loggedUser.getHasBranches())
                .bakeryId(bakeryId) // Set bakeryId based on the condition
                .build();
    }

}
