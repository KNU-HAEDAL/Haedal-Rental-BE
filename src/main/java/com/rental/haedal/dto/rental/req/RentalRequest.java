package com.rental.haedal.dto.rental.req;

import com.rental.haedal.domain.enums.ItemCategory;

import java.time.LocalDate;

public record RentalRequest(
        String name,
        ItemCategory itemCategory,
        LocalDate rentalDate,
        LocalDate returnDate,
        String picture
        // 대여 물품 사진을 찍어야함.
) {
}
