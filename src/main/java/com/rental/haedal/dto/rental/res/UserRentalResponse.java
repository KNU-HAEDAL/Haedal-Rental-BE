package com.rental.haedal.dto.rental.res;

import java.util.List;

public record UserRentalResponse(
        Integer penaltyCount,
        List<ItemResponse> userRentalItemList
) {
}
