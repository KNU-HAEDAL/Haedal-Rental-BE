package com.rental.haedal.dto.auth.req;

public record SignUpRequest(
        String id,
        String password,
        String name,
        String phoneNumber
) {
}
