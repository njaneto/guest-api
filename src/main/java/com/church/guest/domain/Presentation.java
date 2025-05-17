package com.church.guest.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Presentation {

    /**
     * remover parents pois nao ser mais usando nos novos modelos
     */
    @Deprecated
    private String parents;

    private String mother;
    private String father;
    private String children;
}
