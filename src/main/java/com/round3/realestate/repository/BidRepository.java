package com.round3.realestate.repository;

import com.round3.realestate.entity.Auction;
import com.round3.realestate.entity.Bid;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Optional<Bid> findTopByAuction(Auction auction, Sort sort);
    List<Bid> findByAuction(Auction auction);
}
