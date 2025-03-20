package com.rental.haedal.rental.dto.req;

import com.rental.haedal.rental.domain.ItemCategory;

import java.util.Date;

public record RentalRequest(
        String name,
        ItemCategory itemCategory,
        Date rentalDate,
        String picture

        // 날짜 포매팅 수정해야함.
        // 대여 물품 사진을 찍어야함.
) {
}
