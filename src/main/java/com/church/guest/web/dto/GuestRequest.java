package com.church.guest.web.dto;


import com.church.guest.domain.Person;
import com.church.guest.domain.Prayer;
import com.church.guest.domain.Presentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestRequest {

    @NotNull
    @Min(1)
    private Integer guestType;
    private Person person;
    private Prayer prayer;
    private Presentation presentation;
    private String message;

}
