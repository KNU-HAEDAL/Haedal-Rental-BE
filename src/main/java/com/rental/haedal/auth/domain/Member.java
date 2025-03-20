package com.rental.haedal.auth.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false, unique = true)
    private String usedId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "penaltyCount")
    private Integer penaltyCount;

    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberType authority;

}
