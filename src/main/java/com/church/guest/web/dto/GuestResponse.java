package com.church.guest.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class GuestResponse {

    private String id;
    private String name;
    private String message;
    private String guestType;
    private String createdDate;

}
