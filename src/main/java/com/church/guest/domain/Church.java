package com.church.guest.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Church {

    private Boolean attend;
    private Boolean sector;
    private String name;
    private String sectorName;
    private String reverend;
    private String delegate;

}
