package com.church.guest.service;

import com.church.guest.entity.Event;
import com.church.guest.repository.EventsRepository;
import com.church.guest.web.dto.EventsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

@Service
public class EventsService {

    private final EventsRepository repository;

    @Autowired
    public EventsService( EventsRepository repository ) {
        this.repository = repository;
    }

    public Event save( EventsRequest request ) {
        return repository.save( Event.builder()
                .eventDate( request.getEventDate() )
                .departmentName( request.getDepartmentName() )
                .eventName( request.getEventName() )
                .build() );
    }

    public List< Event > findAllCurrentEvents() {
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.HOUR, -12 );

        var events = repository.findByEventDateAfter( cal.getTime().toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime() );

        return events.stream()
                .sorted( Comparator.comparing( Event :: getEventDate ) )
                .toList();
    }

    public void delete( String id ) {
        repository.deleteById( id );
    }
}
