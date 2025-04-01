package com.round3.realestate.controller;

import com.round3.realestate.payload.auction.AuctionDetailsDto;
import com.round3.realestate.payload.auction.CloseAuctionResultDto;
import com.round3.realestate.payload.auction.CreateAuctionDto;
import com.round3.realestate.payload.auction.CreateAuctionResultDto;
import com.round3.realestate.service.AuctionService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/api/auction/create")
    CreateAuctionResultDto create(
        @NotNull @Validated @RequestBody CreateAuctionDto dto
    ) {
        return auctionService.create(dto);
    }

    @PatchMapping("/api/auction/{auctionId}/close")
    CloseAuctionResultDto close(
        @NotNull @PathVariable() Long auctionId
    ) {
        return auctionService.close(auctionId);
    }

    @GetMapping("/api/auction/{auctionId}")
    AuctionDetailsDto details(
        @NotNull @PathVariable() Long auctionId
    ) {
        return auctionService.details(auctionId);
    }
}
