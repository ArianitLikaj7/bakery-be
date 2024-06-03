package com.example.bakerybe.service;

import com.example.bakerybe.config.JwtService;
import com.example.bakerybe.dto.*;
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


    public CurrentLoggedIn getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = (User) authentication.getPrincipal();

        if (loggedUser instanceof CustomUser) {
            return getLoggedInUserDtoForCustomUser((CustomUser) loggedUser);
        } else {
            return getLoggedInUserDtoForUser(loggedUser);
        }
    }


    public CurrentLoggedInUserDto getLoggedInUserDtoForUser(User user) {
        return CurrentLoggedInUserDto.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .email(user.getUsername())
                .hasBranches(user.getHasBranches())
                .build();
    }

    public CurrentLoggedInCustomUserDto getLoggedInUserDtoForCustomUser(CustomUser customUser) {
        return CurrentLoggedInCustomUserDto.builder()
                .userId(customUser.getId())
                .firstName(customUser.getFirstName())
                .lastName(customUser.getLastName())
                .role(customUser.getRole().name())
                .email(customUser.getUsername())
                .bakeryId(customUser.getBakeryId())
                .build();
    }
}
