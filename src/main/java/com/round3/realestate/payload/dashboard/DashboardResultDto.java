package com.round3.realestate.payload.dashboard;

import com.round3.realestate.payload.employment.EmploymentDataDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardResultDto {
    private final List<DashboardMortgageDto> mortgages;
    private final EmploymentDataDto employmentData;
}
