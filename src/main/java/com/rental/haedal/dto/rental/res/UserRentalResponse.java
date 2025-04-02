package com.rental.haedal.dto.rental.res;

import java.util.List;

public record UserRentalResponse(
        List<ItemResponse> userRentalItemList,
        Integer penaltyCount
) {
}
