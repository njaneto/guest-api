package com.church.guest.web.dto;

import com.church.guest.domain.Person;
import com.church.guest.domain.Prayer;
import com.church.guest.domain.Presentation;
import com.church.guest.enums.GuestType;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GuestResponse {

    private String id;
    private String createdDate;
    private GuestType guestType;
    private Person person;
    private Prayer prayer;
    private Presentation presentation;
    private String message;
    private Boolean announced;

}
