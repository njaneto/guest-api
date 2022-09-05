package com.church.guest.web.dto;


import lombok.*;
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
    private Integer guestType;
    @NotNull
    private Integer visitPeriod;

}
