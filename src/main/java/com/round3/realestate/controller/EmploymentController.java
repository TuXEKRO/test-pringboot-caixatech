package com.round3.realestate.controller;

import com.round3.realestate.payload.employment.EmploymentCreateResultDto;
import com.round3.realestate.payload.employment.EmploymentDto;
import com.round3.realestate.payload.employment.EmploymentUpdateResultDto;
import com.round3.realestate.payload.user.registration.UserRegistrationDto;
import com.round3.realestate.payload.user.registration.UserRegistrationResultDto;
import com.round3.realestate.service.EmploymentService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmploymentController {

    private final EmploymentService employmentService;

    @PostMapping("/api/auth/register")
    public UserRegistrationResultDto register(
        @NotNull @Validated @RequestBody UserRegistrationDto dto
    ) {
        return employmentService.register(dto);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/employment")
    public EmploymentCreateResultDto createEmployment(
        @NotNull @RequestBody EmploymentDto dto,
        @NotNull @AuthenticationPrincipal UserDetails details
    ) {
        return employmentService.create(dto, details);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/api/employment")
    public EmploymentUpdateResultDto updateEmployment(
        @NotNull @RequestBody EmploymentDto dto,
        @NotNull @AuthenticationPrincipal UserDetails details
    ) {
        return employmentService.updateEmployment(dto, details);
    }
}
