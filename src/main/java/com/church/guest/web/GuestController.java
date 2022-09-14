package com.church.guest.web;

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
import java.util.stream.Collectors;

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

    @GetMapping( value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<GuestResponse> findGuests(){
        return service.findAll()
                .stream()
                .map(GuestMapper::toGuestResponse)
                .collect(Collectors.toList());
    }

    @DeleteMapping( value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @CrossOrigin
    public void delete(@Valid @PathVariable( name = "id" ) String id){
        service.delete(id);
    }



}
