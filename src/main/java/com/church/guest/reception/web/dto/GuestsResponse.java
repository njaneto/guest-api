package com.church.guest.reception.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GuestsResponse {

    private List< GuestResponse > guests;
    private Integer size;
}
