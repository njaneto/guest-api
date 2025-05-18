package com.church.guest.web;

import com.church.guest.entity.Sector;
import com.church.guest.enums.Departments;
import com.church.guest.service.GuestService;
import com.church.guest.web.dto.Department;
import com.church.guest.web.dto.SectorRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@CrossOrigin( value = "*" )
@RequestMapping( "/utils" )
public class UtilsController {

    private final GuestService guestService;

    @Autowired
    public UtilsController( GuestService guestService ) {
        this.guestService = guestService;
    }

    @PostMapping( value = "/sector", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.CREATED )
    @Secured( "ROLE_USER_WRITER" )
    public Sector saveGuest( @Valid @RequestBody SectorRequest sectorRequest ) {
        return guestService.saveSector( sectorRequest );
    }


    @GetMapping( value = "/sectors", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_READ" )
    public List< Sector > findSectors() {
        return guestService.findAllSectors();
    }

    @GetMapping( value = "/departments", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_READ" )
    public List< Department > departments() {

        return Arrays.stream( Departments.values() )
                .map( departments -> new Department( departments.getCode(),
                        departments.getDepartment(),
                        departments.getDesc() ) )
                .toList();
    }


}
