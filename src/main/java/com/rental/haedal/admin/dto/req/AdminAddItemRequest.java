package com.rental.haedal.admin.dto.req;

import com.rental.haedal.rental.domain.ItemCategory;

public record AdminAddItemRequest(
        Long itemId,
        ItemCategory itemCategory
) {
}
