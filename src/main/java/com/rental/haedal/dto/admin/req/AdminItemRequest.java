package com.rental.haedal.dto.admin.req;

import com.rental.haedal.domain.enums.ItemStatus;

public record AdminItemRequest(
        ItemStatus itemStatus,
        Long itemId
) {
}
