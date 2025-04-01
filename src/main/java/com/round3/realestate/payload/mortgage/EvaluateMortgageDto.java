package com.round3.realestate.payload.mortgage;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class EvaluateMortgageDto {
    @NotNull
    private Long propertyId;
    @NotNull
    @Positive
    private Integer years;
}
