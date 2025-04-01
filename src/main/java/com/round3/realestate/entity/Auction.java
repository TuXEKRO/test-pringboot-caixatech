package com.round3.realestate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "current_highest_bid", columnDefinition = "decimal(10,2)")
    BigDecimal currentHighestBid;

    @Column(name = "end_time")
    LocalDateTime endTime;

    @Column(name = "min_increment", columnDefinition = "decimal(10,2)")
    BigDecimal minIncrement;

    @Column(name = "start_time")
    LocalDateTime startTime;

    @Column(name = "starting_price", columnDefinition = "decimal(10,2)")
    BigDecimal startingPrice;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    AuctionStatus status;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "property_id",
        referencedColumnName = "id",
        unique = true,
        nullable = false)
    private Property property;
}
