package com.round3.realestate.service;

import com.round3.realestate.payload.employment.EmploymentCreateResultDto;
import com.round3.realestate.payload.employment.EmploymentDto;
import com.round3.realestate.payload.employment.EmploymentUpdateResultDto;
import com.round3.realestate.payload.user.registration.UserRegistrationDto;
import com.round3.realestate.payload.user.registration.UserRegistrationResultDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface EmploymentService {
    UserRegistrationResultDto register(UserRegistrationDto dto);

    EmploymentCreateResultDto create(EmploymentDto dto, UserDetails details);

    EmploymentUpdateResultDto updateEmployment(EmploymentDto dto, UserDetails details);
}
