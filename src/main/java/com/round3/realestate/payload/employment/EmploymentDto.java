package com.round3.realestate.payload.employment;

import com.round3.realestate.entity.EmploymentContract;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class EmploymentDto {
    private EmploymentContract contract;
    private BigDecimal salary;
}
