package com.rental.haedal.dto.auth.req;

public record LoginRequest(
        String id,
        String password
) {
}
