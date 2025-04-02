package com.rental.haedal.dto.admin.res;

import com.rental.haedal.domain.enums.ItemStatus;

public record AdminItemStatusChangeResponse(
        long itemId,
        ItemStatus itemStatus
) {
}
