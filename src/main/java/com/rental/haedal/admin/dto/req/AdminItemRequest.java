package com.rental.haedal.admin.dto.req;

import com.rental.haedal.rental.domain.ItemStatus;

public record AdminItemRequest(
        ItemStatus itemStatus,
        Long itemId
) {
}
