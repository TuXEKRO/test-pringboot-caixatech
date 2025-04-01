package com.round3.realestate.converter;

import com.round3.realestate.entity.PropertyAvailability;
import com.round3.realestate.entity.Property;
import com.round3.realestate.payload.dashboard.DashboardPropertyDto;
import com.round3.realestate.payload.property.PropertyResultDataDto;
import com.round3.realestate.util.StringUtils;

public class PropertyConverter {

    public static DashboardPropertyDto toDto(Property property) {
        return DashboardPropertyDto.builder()
            .availability(property.getAvailability())
            .id(property.getId())
            .location(property.getLocation())
            .name(property.getName())
            .price(property.getPrice())
            .rooms(property.getRooms())
            .size(property.getSize())
            .build();
    }

    public static Property toEntity(PropertyResultDataDto dto) {
        return Property.builder()
            .location(dto.getLocation())
            .name(dto.getType())
            .rooms(dto.getRooms())
            .size(dto.getSize())
            .price(StringUtils.parseBigDecimalOrNull(dto.getPrice()))
            .availability(PropertyAvailability.Available)
            .build();
    }
}
