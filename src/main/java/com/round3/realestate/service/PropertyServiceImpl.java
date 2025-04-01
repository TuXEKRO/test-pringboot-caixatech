package com.round3.realestate.service;

import com.round3.realestate.converter.PropertyConverter;
import com.round3.realestate.payload.property.PropertyResultDataDto;
import com.round3.realestate.payload.scrapping.ScrappingDto;
import com.round3.realestate.payload.property.PropertyResultDto;
import com.round3.realestate.repository.PropertyRepository;
import com.round3.realestate.util.IdealistaUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepo;

    @Override
    public PropertyResultDto scrape(ScrappingDto dto) throws IOException {
        Boolean store = dto.getStore();

        PropertyResultDataDto property = IdealistaUtils.scrap(dto.getUrl());

        if (store) {
            propertyRepo.save(PropertyConverter.toEntity(property));
        }

        return PropertyResultDto.builder()
            .data(property)
            .saved(store)
            .build();
    }
}
