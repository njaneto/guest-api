package com.church.guest.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class GuestResponse {

    private LocalDateTime create;
}
