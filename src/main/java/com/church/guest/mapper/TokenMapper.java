package com.church.guest.mapper;

import com.church.guest.web.dto.TokenResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenMapper {
    public static TokenResponse toTokenResponse(String token) {
        return TokenResponse
                .builder()
                .token(token)
                .build();
    }
}
