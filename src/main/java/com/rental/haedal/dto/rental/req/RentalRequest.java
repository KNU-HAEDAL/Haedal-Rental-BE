package com.rental.haedal.dto.rental.req;

import java.time.LocalDate;

public record RentalRequest(
        Long itemId,
        LocalDate rentalDate
) {
}
