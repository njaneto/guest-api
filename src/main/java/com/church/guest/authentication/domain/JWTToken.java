package com.church.guest.authentication.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTToken {

    private String token;

}
