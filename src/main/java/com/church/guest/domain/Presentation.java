package com.church.guest.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Presentation {

    /**
     * @deprecated (remover parents pois nao ser mais usando nos novos modelos)
     */
    @Deprecated(since="2.2.2", forRemoval=true)
    private String parents;

    private String mother;
    private String father;
    private String children;
}
