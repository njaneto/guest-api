package com.church.guest.authentication.mapper;

import com.church.guest.authentication.web.dto.TokenResponse;
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
