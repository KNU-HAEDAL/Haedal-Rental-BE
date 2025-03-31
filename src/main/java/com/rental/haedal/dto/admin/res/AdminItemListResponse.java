package com.rental.haedal.dto.admin.res;

import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;

import java.time.LocalDate;

public record AdminItemListResponse(
        ItemCategory itemCategory,
        String itemName,
        ItemStatus itemStatus,
        LocalDate returnDate,
        String rentalMemberName
) {
}
