package com.round3.realestate.service;

import com.round3.realestate.payload.dashboard.DashboardResultDto;
import com.round3.realestate.payload.mortgage.EvaluateMortgageDto;
import com.round3.realestate.payload.mortgage.MortgageResultApprovedDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface MortgageService {

    MortgageResultApprovedDto mortgage(EvaluateMortgageDto dto, UserDetails details);

    DashboardResultDto dashboard(UserDetails details);
}
