package com.round3.realestate.service;

import com.round3.realestate.payload.auction.AuctionDetailsDto;
import com.round3.realestate.payload.auction.CloseAuctionResultDto;
import com.round3.realestate.payload.auction.CreateAuctionDto;
import com.round3.realestate.payload.auction.CreateAuctionResultDto;

public interface AuctionService {
    CreateAuctionResultDto create(CreateAuctionDto dto);

    CloseAuctionResultDto close(Long auctionId);

    AuctionDetailsDto details(Long auctionId);
}
