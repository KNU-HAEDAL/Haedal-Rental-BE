package com.rental.haedal.rental.dto.req;

import com.rental.haedal.rental.domain.ItemCategory;

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
