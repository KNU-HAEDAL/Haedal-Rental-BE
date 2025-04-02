package com.rental.haedal.dto.rental.res;

import com.rental.haedal.domain.enums.ItemCategory;

import java.time.LocalDate;

public record ItemIndividualResponse(
        long itemId,
        ItemCategory itemCategory,
        String itemName,
        LocalDate dueDate
) {
}
