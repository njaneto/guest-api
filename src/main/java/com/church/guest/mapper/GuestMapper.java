package com.church.guest.mapper;

import com.church.guest.domain.Guest;
import com.church.guest.enums.GuestType;
import com.church.guest.enums.VisitPeriod;
import com.church.guest.web.dto.GuestRequest;
import com.church.guest.web.dto.GuestResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestMapper {
    public static Guest toGuest(GuestRequest request) {

        return request != null ? Guest.builder()
                .guestType(GuestType.ofCode(request.getGuestType()))
                .name(request.getName())
                .message(request.getMessage())
                .createdDate(LocalDateTime.now())
                .visitPeriod(VisitPeriod.ofCode(request.getVisitPeriod()))
                .build() : null ;
    }

    public static GuestResponse toGuestResponse(Guest guest) {
        return guest != null ? GuestResponse.builder()
                .create( guest.getCreatedDate() )
                .build() : null ;
    }
}
