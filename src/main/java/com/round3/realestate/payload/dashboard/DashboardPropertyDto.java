package com.round3.realestate.payload.dashboard;

import com.round3.realestate.entity.PropertyAvailability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class DashboardPropertyDto {
    private final Long id;
    private final String name;
    private final String location;
    private final BigDecimal price;
    private final String size;
    private final String rooms;
    private final PropertyAvailability availability;
}
