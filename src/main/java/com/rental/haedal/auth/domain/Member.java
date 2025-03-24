package com.rental.haedal.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false, unique = true)
    private String userId;

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

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */

    @Builder
    public Member(
            String userId,
            String password,
            String userName,
            String phoneNumber
    ) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.penaltyCount = 0;
        this.authority = MemberType.MEMBER;
    }
}
