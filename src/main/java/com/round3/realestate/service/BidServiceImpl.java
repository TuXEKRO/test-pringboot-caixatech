package com.round3.realestate.service;

import com.round3.realestate.entity.Auction;
import com.round3.realestate.entity.AuctionStatus;
import com.round3.realestate.entity.User;
import com.round3.realestate.exception.AuctionAlreadyClosedException;
import com.round3.realestate.exception.AuctionBidMustNotExceedMinimumException;
import com.round3.realestate.converter.BidConverter;
import com.round3.realestate.messaging.BidPublisher;
import com.round3.realestate.payload.bid.CreateBidDto;
import com.round3.realestate.payload.bid.CreateBidResultDto;
import com.round3.realestate.repository.AuctionRepository;
import com.round3.realestate.repository.BidRepository;
import com.round3.realestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final BidPublisher bidPublisher;

    private void checkAuction(Auction auction) {
        if (AuctionStatus.closed == auction.getStatus()) {
            throw new AuctionAlreadyClosedException();
        }
    }

    private void checkBid(CreateBidDto dto, Auction auction) {
        BigDecimal minIncrement = auction.getMinIncrement();
        BigDecimal currentHighestBid = auction.getCurrentHighestBid();
        BigDecimal bidAmount = dto.getBidAmount();
        if (bidAmount == null ||
            bidAmount.compareTo(currentHighestBid.add(minIncrement)) < 0) {
            throw new AuctionBidMustNotExceedMinimumException();
        }
    }

    @Override
    public CreateBidResultDto bid(CreateBidDto dto, Long auctionId, UserDetails details) {
        User user = userRepository.getUserByUsername(details.getUsername()).orElseThrow();

        Auction auction = auctionRepository.findById(auctionId).orElseThrow(
            AuctionAlreadyClosedException::new
        );

        bidRepository.save(BidConverter.toEntity(dto, auction, user));

        bidPublisher.sendMessage(
            BidConverter.toMessage(dto, auction, user)
        );

        checkBid(dto, auction);

        return new CreateBidResultDto();
    }
}
