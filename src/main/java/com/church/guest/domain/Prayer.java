package com.church.guest.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Prayer {

    public String to;
    public String from;

}
