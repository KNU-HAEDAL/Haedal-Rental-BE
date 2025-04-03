package com.rental.haedal.dto.admin.res;

import com.rental.haedal.domain.enums.ItemCategory;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record AdminItemDetailResponse(
        String rentalMemberName,
        String rentalMemberPhoneNumber,
        String itemName,
        ItemCategory itemCategory,
        LocalDate rentalDate,
        LocalDate returnDate,
        String lastPictureUrl
) {
}
