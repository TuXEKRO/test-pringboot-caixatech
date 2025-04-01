package com.round3.realestate.service;

import com.round3.realestate.payload.property.PropertyResultDto;
import com.round3.realestate.payload.scrapping.ScrappingDto;

import java.io.IOException;

public interface PropertyService {
    PropertyResultDto scrape(ScrappingDto dto) throws IOException;
}
