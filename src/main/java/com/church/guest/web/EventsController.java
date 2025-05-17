package com.church.guest.web;

import com.church.guest.domain.Events;
import com.church.guest.enums.Departments;
import com.church.guest.service.EventsService;
import com.church.guest.web.dto.Department;
import com.church.guest.web.dto.EventsRequest;
import com.church.guest.web.dto.EventsResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public Events saveEvent( @Valid @RequestBody EventsRequest request ) {
        return service.save( request );
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_READ" )
    public EventsResponse findEvents() {

        final List< Events > events = service.findAllCurrentEvents();

        return EventsResponse.builder()
                .events( events )
                .size( events.size() )
                .build();
    }

    @GetMapping( value = "/departments", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_READ" )
    public List< Department > departments() {

        return Arrays.stream( Departments.values() )
                .map( departments -> new Department( departments.getCode(),
                        departments.getDepartment(),
                        departments.getDesc() ) )
                .collect( Collectors.toList() );
    }

    @DeleteMapping( value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.NO_CONTENT )
    @Secured( "ROLE_USER_WRITER" )
    public void delete( @Valid @PathVariable( name = "id" ) String id ) {
        service.delete( id );
    }


}
