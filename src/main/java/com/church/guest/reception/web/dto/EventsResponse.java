package com.church.guest.reception.web.dto;

import com.church.guest.reception.entity.Event;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EventsResponse {
    private List< Event > events;
    private Integer size;
}
