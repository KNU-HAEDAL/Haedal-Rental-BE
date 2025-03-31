package com.rental.haedal.dto.admin.req;

import com.rental.haedal.domain.enums.ItemCategory;

public record AdminAddItemRequest(
        Long itemId,
        ItemCategory itemCategory
) {
}
