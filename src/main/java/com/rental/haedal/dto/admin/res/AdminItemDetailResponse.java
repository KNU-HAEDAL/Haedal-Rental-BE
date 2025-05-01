package com.rental.haedal.dto.admin.res;

import com.rental.haedal.domain.enums.ItemCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record AdminItemDetailResponse(
        String rentalMemberName,
        String rentalMemberPhoneNumber,
        String itemName,
        ItemCategory itemCategory,
        LocalDate rentalDate,
        LocalDateTime returnDateTime,
        String lastPictureUrl
) {
}
