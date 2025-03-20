package com.rental.haedal.auth.dto.req;

public record SignUpRequest(
        String id,
        String password,
        String userName,
        String phoneNumber
) {
}
