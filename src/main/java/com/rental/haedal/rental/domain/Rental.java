package com.rental.haedal.rental.domain;

import com.rental.haedal.auth.domain.Member;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Rental {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;
    
    @Column(name = "rentalDate")
    private LocalDate rentalDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;
}
