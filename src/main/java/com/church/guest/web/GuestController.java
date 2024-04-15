package com.church.guest.web;

import com.church.guest.mapper.GuestMapper;
import com.church.guest.service.GuestService;
import com.church.guest.web.dto.GuestRequest;
import com.church.guest.web.dto.GuestResponse;
import com.church.guest.web.dto.Guests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@CrossOrigin("*")
public class GuestController {

    @Autowired
    private GuestService service;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @Secured("ROLE_USER_WRITER")
    public GuestResponse saveGuest(@Valid @RequestBody GuestRequest request) {
        return GuestMapper.toGuestResponse(service.save(request));
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_USER_WRITER")
    public GuestResponse editGuest(@Valid @PathVariable(name = "id") String id,
                                   @Valid @RequestBody GuestRequest request) {

        return GuestMapper.toGuestResponse(service.editGuest(id, request));
    }

    @PutMapping(value = "/announced/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    //@Secured("ROLE_USER_WRITER")
    public GuestResponse announcedGuest(@Valid @PathVariable(name = "id") String id) {
        return GuestMapper.toGuestResponse(service.announcedGuest(id));
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    //@Secured("ROLE_USER_READ")
    public Guests findGuests() {

        final List<GuestResponse> responses = service.findAll()
                .stream()
                .map(GuestMapper::toGuestResponse)
                .collect(Collectors.toList());

        return GuestMapper.toGuestResponses(responses);
    }

    @GetMapping(value = "/export", produces = "text/csv")
    @ResponseStatus(value = HttpStatus.OK)
    //@Secured("ROLE_USER_WRITER")
    public void exportGuestToCsv(HttpServletResponse response,
                                 @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                 @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                 @Valid @RequestParam(name = "filename") String filename) {

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=".concat(filename));
        response.setContentType("text/csv; charset=UTF-8");
        service.exportGuestToCsv(response, startDate, endDate);

    }

    @GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    //@Secured("ROLE_USER_READ")
    public GuestResponse findGuestById(@Valid @PathVariable(name = "id") String id) {
        return GuestMapper.toGuestResponse(service.findGuestById(id));
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Secured("ROLE_USER_WRITER")
    public void delete(@Valid @PathVariable(name = "id") String id) {
        service.delete(id);
    }


}
