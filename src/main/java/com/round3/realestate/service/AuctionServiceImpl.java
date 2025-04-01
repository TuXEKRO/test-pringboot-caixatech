package com.round3.realestate.service;

import com.round3.realestate.entity.*;
import com.round3.realestate.exception.AuctionAlreadyClosedException;
import com.round3.realestate.exception.AuctionPropertyNotAvailableException;
import com.round3.realestate.exception.AuctionPropertyNotFoundException;
import com.round3.realestate.converter.AuctionConverter;
import com.round3.realestate.converter.BidConverter;
import com.round3.realestate.payload.auction.AuctionDetailsDto;
import com.round3.realestate.payload.auction.CloseAuctionResultDto;
import com.round3.realestate.payload.auction.CreateAuctionDto;
import com.round3.realestate.payload.auction.CreateAuctionResultDto;
import com.round3.realestate.repository.AuctionRepository;
import com.round3.realestate.repository.BidRepository;
import com.round3.realestate.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final PropertyRepository propertyRepository;
    private final BidRepository bidRepository;

    private void checkCreateAuctionDto(CreateAuctionDto dto) {
        if (dto == null || dto.getPropertyId() == null) {
            throw new AuctionPropertyNotFoundException();
        }
    }

    @Override
    public CreateAuctionResultDto create(CreateAuctionDto dto) {
        checkCreateAuctionDto(dto);

        Property property = propertyRepository.findById(dto.getPropertyId()).orElseThrow(
            AuctionPropertyNotFoundException::new
        );

        if (PropertyAvailability.Unavailable.equals(property.getAvailability())) {
            throw new AuctionPropertyNotAvailableException();
        }

        return CreateAuctionResultDto.success(
            auctionRepository.save(AuctionConverter.toEntity(dto, property)).getId()
        );
    }

    @Override
    public CloseAuctionResultDto close(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(
            AuctionAlreadyClosedException::new
        );

        Bid topBid = bidRepository.findTopByAuction(auction, SORT_BID_AMOUNT_DESC)
            .orElse(null);

        closeAuction(auction);

        if (topBid == null)
            return CloseAuctionResultDto.success();
        else
            return BidConverter.toCloseAuctionResultDto(topBid);
    }

    private void closeAuction(Auction auction) {
        Property property = auction.getProperty();
        property.setAvailability(PropertyAvailability.Unavailable);
        auction.setStatus(AuctionStatus.closed);
        auction.setProperty(property);
        auctionRepository.save(auction);
    }

    @Override
    public AuctionDetailsDto details(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        List<Bid> bids = bidRepository.findByAuction(auction);
        return AuctionConverter.toDto(auction, bids);
    }

    private static final Sort SORT_BID_AMOUNT_DESC = Sort.by(
        Sort.Order.desc("bidAmount"));
}
