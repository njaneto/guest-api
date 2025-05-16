package com.church.guest.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Presentation {

    private String mother;
    private String father;
    private String children;
}
