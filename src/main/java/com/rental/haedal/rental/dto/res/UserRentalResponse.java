package com.rental.haedal.rental.dto.res;

import java.util.List;

public record UserRentalResponse(
        Integer penaltyCount,
        List<ItemResponse> userRentalItemList
) {
}
