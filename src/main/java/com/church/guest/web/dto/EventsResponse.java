package com.church.guest.web.dto;

import com.church.guest.domain.Events;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EventsResponse {
    private List< Events > events;
    private Integer size;
}
