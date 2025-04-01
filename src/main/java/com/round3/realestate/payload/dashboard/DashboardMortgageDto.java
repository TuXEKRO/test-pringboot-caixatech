package com.round3.realestate.payload.dashboard;

import com.round3.realestate.payload.user.session.UserSessionResultDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class DashboardMortgageDto {
    private final Long id;
    private final DashboardPropertyDto property;
    private final UserSessionResultDto user;
    private final BigDecimal monthlyPayment;
    private final Integer numberOfMonths;
}
