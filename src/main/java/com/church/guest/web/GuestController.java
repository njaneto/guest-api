package com.church.guest.web;

import com.church.guest.domain.Sector;
import com.church.guest.mapper.GuestMapper;
import com.church.guest.service.GuestService;
import com.church.guest.web.dto.GuestRequest;
import com.church.guest.web.dto.GuestResponse;
import com.church.guest.web.dto.Guests;
import com.church.guest.web.dto.SectorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@CrossOrigin(value = "*")
public class GuestController {

    private final GuestService service;

    @Autowired
    public GuestController( GuestService service ) {
        this.service = service;
    }

    @PostMapping( value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.CREATED )
    @Secured( "ROLE_USER_WRITER" )
    //@CrossOrigin(value = "*", allowedHeaders = "*")
    public GuestResponse saveGuest( @Valid @RequestBody GuestRequest request ) {
        return GuestMapper.toGuestResponse( service.save( request ) );
    }

    @PutMapping( value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_WRITER" )
    //@CrossOrigin(value = "*", allowedHeaders = "*")
    public GuestResponse editGuest( @Valid @PathVariable( name = "id" ) String id,
                                    @Valid @RequestBody GuestRequest request ) {

        return GuestMapper.toGuestResponse( service.editGuest( id, request ) );
    }

    @PutMapping( value = "/announced/{id}" )
    @ResponseStatus( value = HttpStatus.OK )
    public GuestResponse announcedGuest( @Valid @PathVariable( name = "id" ) String id ) {
        return GuestMapper.toGuestResponse( service.announcedGuest( id, Boolean.TRUE ) );
    }

    @PutMapping( value = "/unread/{id}" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_WRITER" )
    //@CrossOrigin(value = "*", allowedHeaders = "*")
    public GuestResponse unreadGuest( @Valid @PathVariable( name = "id" ) String id ) {
        return GuestMapper.toGuestResponse( service.announcedGuest( id, Boolean.FALSE ) );
    }


    @GetMapping( value = "/find", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    public Guests findGuests() {

        final List< GuestResponse > responses = service.findAllAnnouncedFalse()
                .stream()
                .map( GuestMapper :: toGuestResponse )
                .collect( Collectors.toList() );

        return GuestMapper.toGuestResponses( responses );
    }


    @GetMapping( value = "/sectors", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    public List< Sector > findSectors() {
        return service.findAllSectors();
    }

    @PostMapping( value = "/sector", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.CREATED )
    @Secured( "ROLE_USER_WRITER" )
    //@CrossOrigin(value = "*", allowedHeaders = "*")
    public Sector saveGuest( @Valid @RequestBody SectorRequest sectorRequest ) {
        return service.saveSector( Sector.builder().value( sectorRequest.getValue() ).build() );
    }


    @GetMapping( value = "/export", produces = "text/csv" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_WRITER" )
    //@CrossOrigin(value = "*", allowedHeaders = "*")
    public void exportGuestToCsv( HttpServletResponse response ) {

        response.setHeader( HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=".concat( "recepcao-".concat( LocalDateTime.now().toString() ) ) );
        response.setContentType( "text/csv; charset=UTF-8" );
        service.exportGuestToCsv( response );

    }

    @GetMapping( value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    public GuestResponse findGuestById( @Valid @PathVariable( name = "id" ) String id ) {
        return GuestMapper.toGuestResponse( service.findGuestById( id ) );
    }

    @GetMapping( value = "/history", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "ROLE_USER_WRITER" )
    //@CrossOrigin(value = "*", allowedHeaders = "*")
    public Guests history() {

        final List< GuestResponse > responses = service.findAllDayHistory()
                .stream()
                .map( GuestMapper :: toGuestResponse )
                .collect( Collectors.toList() );

        return GuestMapper.toGuestResponses( responses );
    }

    @DeleteMapping( value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.NO_CONTENT )
    @Secured( "ROLE_USER_WRITER" )
    //@CrossOrigin(value = "*", allowedHeaders = "*")
    public void delete( @Valid @PathVariable( name = "id" ) String id ) {
        service.delete( id );
    }


}
