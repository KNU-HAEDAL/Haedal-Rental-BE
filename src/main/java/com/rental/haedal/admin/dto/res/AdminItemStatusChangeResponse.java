package com.rental.haedal.admin.dto.res;

import com.rental.haedal.rental.domain.ItemStatus;

public record AdminItemStatusChangeResponse(
        ItemStatus itemStatus
) {
}
