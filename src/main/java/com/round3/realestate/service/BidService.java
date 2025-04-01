package com.round3.realestate.service;

import com.round3.realestate.payload.bid.CreateBidDto;
import com.round3.realestate.payload.bid.CreateBidResultDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface BidService {
    CreateBidResultDto bid(CreateBidDto dto, Long auctionId, UserDetails details);
}
