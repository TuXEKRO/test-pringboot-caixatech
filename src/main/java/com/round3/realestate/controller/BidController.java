package com.round3.realestate.controller;

import com.round3.realestate.payload.bid.CreateBidDto;
import com.round3.realestate.payload.bid.CreateBidResultDto;
import com.round3.realestate.service.BidService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BidController {

    private final BidService bidService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/auction/{auctionId}/bid")
    CreateBidResultDto bid(
        @NotNull @PathVariable() Long auctionId,
        @NotNull @Validated @RequestBody CreateBidDto dto,
        @NotNull @AuthenticationPrincipal UserDetails details
    ) {
        return bidService.bid(dto, auctionId, details);
    }
}
