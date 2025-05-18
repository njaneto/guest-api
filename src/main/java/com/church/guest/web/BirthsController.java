package com.church.guest.web;

import com.church.guest.service.BirthService;
import com.church.guest.web.dto.BirthRequest;
import com.church.guest.web.dto.BirthResponse;
import com.church.guest.web.dto.BirthsResponse;
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
@RequestMapping( "/births" )
public class BirthsController {

    private final BirthService service;

    @Autowired
    public BirthsController( BirthService service ) {
        this.service = service;
    }

    @PostMapping( value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.CREATED )
    @Secured( "ROLE_USER_WRITER" )
    public BirthResponse saveEvent( @Valid @RequestBody BirthRequest request ) {
        return service.save( request );
    }

    @GetMapping( value = "/birthdays-week", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_READ" )
    public BirthsResponse findBirthdays() {

        final List< BirthResponse > births = service.findAllCurrentBirths();

        return BirthsResponse.builder()
                .births( births )
                .size( births.size() )
                .build();
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_READ" )
    public BirthsResponse findAllBirthdays() {

        final List< BirthResponse > births = service.findAllBirths();

        return BirthsResponse.builder()
                .births( births )
                .size( births.size() )
                .build();
    }

    @DeleteMapping( value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.NO_CONTENT )
    @Secured( "ROLE_USER_WRITER" )
    public void delete( @Valid @PathVariable( name = "id" ) String id ) {
        service.delete( id );
    }

}
