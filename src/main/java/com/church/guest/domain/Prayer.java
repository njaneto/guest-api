package com.church.guest.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Prayer {

    private String to;
    private String from;

}
