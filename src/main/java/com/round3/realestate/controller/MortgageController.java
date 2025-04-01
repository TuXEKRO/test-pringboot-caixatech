package com.round3.realestate.controller;

import com.round3.realestate.payload.dashboard.DashboardResultDto;
import com.round3.realestate.payload.mortgage.EvaluateMortgageDto;
import com.round3.realestate.payload.mortgage.MortgageResultApprovedDto;
import com.round3.realestate.service.MortgageService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MortgageController {

    private final MortgageService mortgageService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/mortgage")
    MortgageResultApprovedDto mortgage(
        @NotNull @Validated @RequestBody EvaluateMortgageDto dto,
        @NotNull @AuthenticationPrincipal UserDetails details
    ) {
        return mortgageService.mortgage(dto, details);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/user/dashboard")
    DashboardResultDto dashboard(
        @NotNull @AuthenticationPrincipal UserDetails details
    ) {
        return mortgageService.dashboard(details);
    }
}
