package com.church.guest.reception.web.dto;

import com.church.guest.reception.domain.Person;
import com.church.guest.reception.domain.Prayer;
import com.church.guest.reception.domain.Presentation;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GuestResponse {

    private String id;
    private String createdDate;
    private Integer guestType;
    private Integer sort;
    private Person person;
    private Prayer prayer;
    private Presentation presentation;
    private String message;
    private Boolean announced;

}
