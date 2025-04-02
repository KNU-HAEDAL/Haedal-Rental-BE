package com.rental.haedal.dto.rental.res;

import java.util.List;

public record UserRentalResponse(
        List<ItemIndividualResponse> userRentalItemList,
        Integer penaltyCount
) {
}
