package com.rental.haedal.dto.admin.req;

import com.rental.haedal.domain.enums.ItemCategory;

public record AdminAddItemRequest(
        String itemName,
        ItemCategory itemCategory
) {
}
