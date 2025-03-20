package com.rental.haedal.admin.dto.res;

import com.rental.haedal.rental.domain.ItemCategory;

import java.util.Date;

public record AdminItemDetailResponse(
        String rentalMemberName,
        String rentalMemberPhoneNumber,
        String itemName,
        ItemCategory itemCategory,
        Date rentalDate,
        String picture
) {
}
