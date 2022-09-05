package com.church.guest.web;

import com.church.guest.domain.Guest;
import com.church.guest.enums.VisitPeriod;
import com.church.guest.mapper.GuestMapper;
import com.church.guest.service.GuestService;
import com.church.guest.web.dto.GuestRequest;
import com.church.guest.web.dto.GuestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class GuestController {

    @Autowired
    private GuestService service;

    @PostMapping( value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public GuestResponse saveGuest(@Valid @RequestBody GuestRequest request){
        return GuestMapper.toGuestResponse( service.save( request ));
    }

    @GetMapping( value = "/find/{visitPeriod}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Guest> findGuests(@Valid @PathVariable( name = "visitPeriod" ) Integer visitPeriod){
        return service.findAll( VisitPeriod.ofCode(visitPeriod) );
    }
}
