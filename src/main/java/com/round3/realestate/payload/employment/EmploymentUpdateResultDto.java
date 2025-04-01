package com.round3.realestate.payload.employment;

import lombok.*;

@Getter
public class EmploymentUpdateResultDto {

    private final EmploymentDataDto employmentData;
    private final String message = SUCCESS_MESSAGE;
    private final Boolean success = true;

    private EmploymentUpdateResultDto(
        EmploymentDataDto employmentData
    ) {
        this.employmentData = employmentData;
    }

    public static EmploymentUpdateResultDto success(
        EmploymentDataDto employmentData
    ) {
        return new EmploymentUpdateResultDto(employmentData);
    }

    public static final String SUCCESS_MESSAGE = "Employment data updated successfully";
}
