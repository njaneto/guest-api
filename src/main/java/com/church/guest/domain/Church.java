package com.church.guest.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Church {

    private Boolean attend;
    private String name;
    private String sector;
}
