package com.rental.haedal.rental.dto.res;

import com.rental.haedal.rental.domain.ItemCategory;
import com.rental.haedal.rental.domain.ItemStatus;

public record ItemResponse(
        long itemId,
        String itemName,
        ItemCategory itemCategory,
        ItemStatus itemStatus
) {
}
