package com.rental.haedal.rental.domain;

import com.rental.haedal.auth.domain.Member;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Rental {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;

    // JSON에서 직렬화 필요
    @Column(name = "rentalDuration")
    private Date rentalDuration;
}
