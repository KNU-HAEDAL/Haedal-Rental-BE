package com.rental.haedal.dto.rental.res;

import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;

public record ItemResponse(
        long itemId,
        String itemName,
        ItemCategory itemCategory,
        ItemStatus itemStatus
) {
}
