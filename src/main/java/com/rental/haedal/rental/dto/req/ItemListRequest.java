package com.rental.haedal.rental.dto.req;

import com.rental.haedal.rental.domain.ItemCategory;

public record ItemListRequest(
        ItemCategory itemCategory
) {
}
