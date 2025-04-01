package com.round3.realestate.converter;

import com.round3.realestate.entity.Employment;
import com.round3.realestate.entity.EmploymentStatus;
import com.round3.realestate.payload.employment.EmploymentCreateResultDto;
import com.round3.realestate.payload.employment.EmploymentDataDto;
import com.round3.realestate.payload.employment.EmploymentUpdateResultDto;
import com.round3.realestate.payload.user.registration.UserRegistrationDto;

public class EmploymentConverter {

    public static Employment toEntity(UserRegistrationDto dto, String password) {
        return Employment.builder()
            .employmentStatus(EmploymentStatus.unemployed)
            .user(UserConverter.toEntity(dto, password))
            .build();
    }

    public static EmploymentDataDto toDto(Employment employment) {
        return EmploymentDataDto.builder()
            .contract(employment.getContract())
            .employmentStatus(employment.getEmploymentStatus())
            .id(employment.getId())
            .netMonthly(employment.getNetMonthly())
            .salary(employment.getSalary())
            .user(UserConverter.toSessionDto(employment.getUser()))
            .build();
    }

    public static EmploymentCreateResultDto toCreateResultDto(Employment employment) {
        return EmploymentCreateResultDto.success(toDto(employment));
    }

    public static EmploymentUpdateResultDto toUpdateResultDto(Employment employment) {
        return EmploymentUpdateResultDto.success(toDto(employment));
    }
}
