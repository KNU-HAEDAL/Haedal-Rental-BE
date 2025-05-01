package com.rental.haedal.dto.admin.res;

import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record AdminItemResponse(
        Long itemId,
        String itemName,
        ItemCategory itemCategory,
        ItemStatus itemStatus,
        LocalDate rentalDate,
        LocalDate dueDate,
        LocalDateTime returnDateTime,
        String rentalMemberName
) {
}
