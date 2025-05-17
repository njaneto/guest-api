package com.church.guest.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventsRequest {

    @NotBlank
    private String eventName;
    @NotBlank
    private String departmentName;
    @NotNull
    @FutureOrPresent(message = "Deve ser uma data no presente ou no futuro")
    private LocalDateTime eventDate;

}
