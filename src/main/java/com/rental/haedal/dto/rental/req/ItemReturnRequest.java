package com.rental.haedal.dto.rental.req;

public record ItemReturnRequest(
        Long itemId,
        String base64Image
) {
}
