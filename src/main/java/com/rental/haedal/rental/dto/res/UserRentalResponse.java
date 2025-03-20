package com.rental.haedal.rental.dto.res;

import com.rental.haedal.rental.domain.ItemCategory;
import com.rental.haedal.rental.domain.ItemStatus;

public record UserRentalResponse(
        ItemCategory itemCategory,
        String itemName,
        ItemStatus itemStatus,
        Integer penaltyCount
) {
}
