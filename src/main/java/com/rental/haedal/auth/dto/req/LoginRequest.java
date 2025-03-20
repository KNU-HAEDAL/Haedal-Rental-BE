package com.rental.haedal.auth.dto.req;

public record LoginRequest(
        String id,
        String password
) {
}
