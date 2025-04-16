package com.church.guest.web.dto;


import com.church.guest.domain.Person;
import com.church.guest.domain.Prayer;
import com.church.guest.domain.Presentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


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
