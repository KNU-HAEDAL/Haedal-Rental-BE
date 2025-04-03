package com.rental.haedal.dto.admin.res;

import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record AdminItemResponse(
        Long itemId,
        String itemName,
        ItemCategory itemCategory,
        ItemStatus itemStatus,
        LocalDate rentalDate,
        LocalDate dueDate,
        LocalDate returnDate,
        String rentalMemberName
) {
}
