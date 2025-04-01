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
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "bid_amount", columnDefinition = "decimal(10,2)")
    BigDecimal bidAmount;

    private LocalDateTime timestamp;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "auction_id", referencedColumnName = "id", nullable = false)
    private Auction auction;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
