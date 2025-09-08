package com.church.guest.reception.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Prayer {

    private String to;
    private String from;

}
