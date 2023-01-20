package com.church.guest.web.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GuestResponse {

    private String id;
    private String name;
    private String message;
    private String guestType;
    private String createdDate;

}
