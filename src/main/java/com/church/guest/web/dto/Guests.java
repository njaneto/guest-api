package com.church.guest.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Guests {

    private List<GuestResponse> guests;
    private Integer size;
}
