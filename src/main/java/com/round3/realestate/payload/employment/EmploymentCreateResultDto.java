package com.round3.realestate.payload.employment;

import lombok.Getter;

@Getter
public class EmploymentCreateResultDto {

    private final EmploymentDataDto employmentData;
    private final String message = SUCCESS_MESSAGE;

    private EmploymentCreateResultDto(EmploymentDataDto employmentData) {
        this.employmentData = employmentData;
    }

    public static EmploymentCreateResultDto success(EmploymentDataDto employmentData) {
        return new EmploymentCreateResultDto(employmentData);
    }

    public static final String SUCCESS_MESSAGE = "Employment data updated successfully";
}
