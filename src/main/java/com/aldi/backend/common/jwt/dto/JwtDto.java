package com.aldi.backend.common.jwt.dto;

public record JwtDto( //생성된 jwt 를 담는 DTO
        String accessToken,
        String refreshToken
) {
}
