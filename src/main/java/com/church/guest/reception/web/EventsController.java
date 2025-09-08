package com.church.guest.reception.web;

import com.church.guest.reception.entity.Event;
import com.church.guest.reception.service.EventsService;
import com.church.guest.reception.web.dto.EventsRequest;
import com.church.guest.reception.web.dto.EventsResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@CrossOrigin( value = "*" )
@RequestMapping( "/events" )
public class EventsController {

    private final EventsService service;

    @Autowired
    public EventsController( EventsService service ) {
        this.service = service;
    }

    @PostMapping( value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.CREATED )
    @Secured( "ROLE_USER_WRITER" )
    public Event saveEvent( @Valid @RequestBody EventsRequest request ) {
        return service.save( request );
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_READ" )
    public EventsResponse findEvents() {

        final List< Event > events = service.findAllCurrentEvents();

        return EventsResponse.builder()
                .events( events )
                .size( events.size() )
                .build();
    }

    @DeleteMapping( value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.NO_CONTENT )
    @Secured( "ROLE_USER_WRITER" )
    public void delete( @Valid @PathVariable( name = "id" ) String id ) {
        service.delete( id );
    }


}
