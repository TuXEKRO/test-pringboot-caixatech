package com.round3.realestate.converter;

import com.round3.realestate.entity.Mortgage;
import com.round3.realestate.entity.Property;
import com.round3.realestate.entity.User;
import com.round3.realestate.payload.dashboard.DashboardMortgageDto;

import java.math.BigDecimal;

public class MortgageConverter {

    public static Mortgage toEntity(
        BigDecimal monthlyPayment,
        int numberOfMonth,
        Property property,
        User user
    ) {
        return Mortgage.builder()
            .monthlyPayment(monthlyPayment)
            .numberOfMonths(numberOfMonth)
            .property(property)
            .user(user)
            .build();
    }

    public static DashboardMortgageDto toDto(Mortgage mortgage) {
        return DashboardMortgageDto.builder()
            .id(mortgage.getId())
            .monthlyPayment(mortgage.getMonthlyPayment())
            .numberOfMonths(mortgage.getNumberOfMonths())
            .user(UserConverter.toSessionDto(mortgage.getUser()))
            .property(PropertyConverter.toDto(mortgage.getProperty()))
            .build();
    }
}
