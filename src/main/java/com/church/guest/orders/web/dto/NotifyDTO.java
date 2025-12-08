package com.church.guest.orders.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyDTO {
    private String message;
}
