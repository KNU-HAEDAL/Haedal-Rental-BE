package com.rental.haedal.domain;

import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "itemName", nullable = false)
    private String itemName;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    @Column(name = "rentalStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus status;
}
