package com.church.guest.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BirthsResponse {

    private List< BirthResponse > births;
    private Integer size;
}
