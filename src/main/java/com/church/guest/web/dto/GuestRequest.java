package com.church.guest.web.dto;


import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestRequest {

    @NotEmpty
    private String name;
    private String message;
    @NotNull
    @Min(1)
    private Integer guestType;
    @NotNull
    @Min(1)
    private Integer visitPeriod;

}
