package com.church.guest.mapper;

import com.church.guest.domain.Guest;
import com.church.guest.enums.GuestType;
import com.church.guest.web.dto.GuestRequest;
import com.church.guest.web.dto.GuestResponse;
import com.church.guest.web.dto.Guests;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestMapper {
    public static Guest toGuest(GuestRequest request) {

        return request != null ? Guest.builder()
                .guestType(GuestType.ofCode(request.getGuestType()))
                .presentation(request.getPresentation())
                .person(request.getPerson())
                .prayer(request.getPrayer())
                .message(request.getMessage())
                .announced(Boolean.FALSE)
                .createdDate(LocalDateTime.now())
                .build() : null ;
    }

    public static GuestResponse toGuestResponse(Guest guest) {
        return guest != null ? GuestResponse.builder()
                .id(guest.getId())
                .guestType(guest.getGuestType().getCode())
                .presentation(guest.getPresentation())
                .person(guest.getPerson())
                .prayer(guest.getPrayer())
                .message(guest.getMessage())
                .createdDate(guest.getCreatedDate().toString())
                .announced(Optional.ofNullable(guest.getAnnounced()).orElse(false))
                .build() : null ;
    }

    public static Guests toGuestResponses(List<GuestResponse> guests) {
        return Guests.builder()
                .guests(guests)
                .size(guests.size())
                .build();
    }
}
