package com.church.guest.mapper;

import com.church.guest.domain.*;
import com.church.guest.enums.BirthdayType;
import com.church.guest.enums.GuestType;
import com.church.guest.web.dto.GuestCsv;
import com.church.guest.web.dto.GuestRequest;
import com.church.guest.web.dto.GuestResponse;
import com.church.guest.web.dto.Guests;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                .sort( guest.getGuestType().getSort() )
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

    public static GuestCsv toGuestCsv(Guest guest) {
        return guest != null ? GuestCsv.builder()
                .id(guest.getId())
                .createdDate(guest.getCreatedDate().format( DateTimeFormatter.ofPattern( "dd-MM-yyyy HH:mm:ss" ) ))
                .guestType(guest.getGuestType().getDesc())
                .name(Optional.ofNullable(guest.getPerson()).map(Person::getName).orElse(""))
                .invitedBy(Optional.ofNullable(guest.getPerson()).map(Person::getInvitedBy).orElse(""))
                .attend(Optional.ofNullable(guest.getPerson()).map(person -> Optional.ofNullable(person.getChurch()).map(Church::getAttend).orElse(false)).orElse(false))
                .churchName(Optional.ofNullable(guest.getPerson()).map(person -> Optional.ofNullable(person.getChurch()).map(Church::getName).orElse("")).orElse(""))
                .sectorName( Optional.ofNullable(guest.getPerson()).map(person -> Optional.ofNullable(person.getChurch()).map(Church::getSectorName).orElse("")).orElse("") )

                .birthdayType(Optional.ofNullable(guest.getPerson())
                        .map(person -> Optional.ofNullable(person.getBirthday())
                                .map(birthday -> Optional.ofNullable(birthday.getType())
                                        .map(BirthdayType::getDesc)
                                        .orElse(""))
                                .orElse(""))
                        .orElse(""))

                .age(Optional.ofNullable(guest.getPerson())
                        .map(person -> Optional.ofNullable(person.getBirthday())
                                .map(Birthday::getAge)
                                .orElse("0"))
                        .orElse("0"))


                .to(Optional.ofNullable(guest.getPrayer()).map(Prayer::getTo).orElse(""))
                .from(Optional.ofNullable(guest.getPrayer()).map(Prayer::getFrom).orElse(""))
                .parents(Optional.ofNullable(guest.getPresentation()).map(Presentation:: getMother ).orElse(""))
                .children(Optional.ofNullable(guest.getPresentation()).map(Presentation::getChildren).orElse(""))
                .message(formatMessage(guest.getMessage()))
                .announced(guest.getAnnounced())
                .build() : null;
    }

    private static String formatMessage(String message) {
        if(message == null || message.trim().isEmpty() ){
            return message;
        }
        return message.replace("\n", " ");
    }
}
