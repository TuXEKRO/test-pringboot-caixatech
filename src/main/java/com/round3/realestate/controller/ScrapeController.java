package com.round3.realestate.controller;

import com.round3.realestate.payload.scrapping.ScrappingDto;
import com.round3.realestate.payload.property.PropertyResultDto;
import com.round3.realestate.service.PropertyService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ScrapeController {

    private final PropertyService propertyService;

    @PostMapping("/api/scrape")
    PropertyResultDto scrape(
        @NotNull @Validated @RequestBody ScrappingDto dto
    ) throws IOException {
        return propertyService.scrape(dto);
    }
}
