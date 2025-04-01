package com.round3.realestate.controller;

import com.round3.realestate.payload.user.login.UserLoginDto;
import com.round3.realestate.payload.user.login.UserLoginResultDto;
import com.round3.realestate.payload.user.session.UserSessionResultDto;
import com.round3.realestate.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/auth/login")
    public UserLoginResultDto login(
        @NotNull @Validated @RequestBody UserLoginDto dto
    ) {
        return userService.login(dto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/user/me")
    public UserSessionResultDto me(
        @NotNull @AuthenticationPrincipal UserDetails details
    ) {
        return userService.me(details);
    }
}
