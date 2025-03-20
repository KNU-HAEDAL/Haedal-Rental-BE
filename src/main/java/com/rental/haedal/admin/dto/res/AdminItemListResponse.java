package com.rental.haedal.admin.dto.res;

import com.rental.haedal.rental.domain.ItemCategory;
import com.rental.haedal.rental.domain.ItemStatus;

import java.util.Date;

public record AdminItemListResponse(
        ItemCategory itemCategory,
        String itemName,
        ItemStatus itemStatus,
        Date rentalDate,
        String rentalMemberName
) {
}
