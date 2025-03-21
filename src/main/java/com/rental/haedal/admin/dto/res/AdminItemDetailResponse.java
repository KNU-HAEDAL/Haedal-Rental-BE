package com.rental.haedal.admin.dto.res;

import com.rental.haedal.rental.domain.ItemCategory;

import java.time.LocalDate;

public record AdminItemDetailResponse(
        String rentalMemberName,
        String rentalMemberPhoneNumber,
        String itemName,
        ItemCategory itemCategory,
        LocalDate rentalDate,
        LocalDate returnDate,
        String picture
) {
}
