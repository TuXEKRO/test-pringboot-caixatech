package com.round3.realestate.payload.employment;

import com.round3.realestate.entity.EmploymentContract;
import com.round3.realestate.entity.EmploymentStatus;
import com.round3.realestate.payload.user.session.UserSessionResultDto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentDataDto {
    Long id;
    UserSessionResultDto user;
    EmploymentContract contract;
    BigDecimal salary;
    BigDecimal netMonthly;
    EmploymentStatus employmentStatus;
}
