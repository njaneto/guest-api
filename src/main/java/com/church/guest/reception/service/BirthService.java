package com.church.guest.reception.service;

import com.church.guest.reception.entity.Birth;
import com.church.guest.exceptions.GuestRuntimeException;
import com.church.guest.reception.repository.BirthdayRepository;
import com.church.guest.reception.web.dto.BirthRequest;
import com.church.guest.reception.web.dto.BirthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BirthService {

    private final BirthdayRepository repository;

    @Autowired
    public BirthService( BirthdayRepository repository ) {
        this.repository = repository;
    }

    public BirthResponse save( BirthRequest request ) {

        repository.findByNameAndDateOfBirth( request.getName(), request.getDateOfBirth() )
                .ifPresent( birth -> {
                    throw new GuestRuntimeException( "Aniversário já registrado para: " + birth.getName(), HttpStatus.CONFLICT );
                } );

        var birth = repository.save( Birth.builder()
                .name( request.getName() )
                .dateOfBirth( request.getDateOfBirth() )
                .day( request.getDateOfBirth().getDayOfMonth() )
                .month( request.getDateOfBirth().getMonthValue() )
                .year( request.getDateOfBirth().getYear() )
                .build() );

        return BirthResponse.builder()
                .id( birth.getId() )
                .name( birth.getName() )
                .age( getAge( birth.getDateOfBirth() ) )
                .birthday( getBirthday( birth.getDateOfBirth() ) )
                .dateOfBirth( birth.getDateOfBirth() )
                .createdDate( birth.getCreatedDate() )
                .build();
    }

    private LocalDate getBirthday( LocalDate dateOfBirth ) {

        return LocalDate.of( LocalDate.now().getYear(),
                dateOfBirth.getMonth(),
                dateOfBirth.getDayOfMonth() );
    }

    private static LocalDate getNextBirthday( LocalDate birthday ) {
        return !birthday.isBefore( LocalDate.now() ) ? birthday : birthday.plusYears( 1 );
    }

    private int getAge( LocalDate dateOfBirth ) {
        return Period.between( dateOfBirth, getBirthday( dateOfBirth ) ).getYears();
    }

    public List< BirthResponse > findAllCurrentBirths() {
        var today = LocalDate.now();
        var startDate = today.minusDays( 7 );

        var currentMonth = today.getMonthValue();
        var currentDay = today.getDayOfMonth();

        var previousMonth = startDate.getMonthValue();
        var startDay = startDate.getDayOfMonth();

        List< Birth > births;

        if( currentMonth == previousMonth ) {
            births = repository.findByMonthAndDayBetween( currentMonth, startDay, currentDay );
        } else {
            // Virou o mês: precisa consultar dois meses
            List< Birth > part1 = repository.findByMonthAndDayBetween( previousMonth, startDay, 31 );
            List< Birth > part2 = repository.findByMonthAndDayBetween( currentMonth, 1, currentDay );
            births = Stream.concat( part1.stream(), part2.stream() ).toList();
        }

        return births.stream()
                .sorted( Comparator.comparing( Birth :: getMonth )
                        .thenComparing( Birth :: getDay ) )
                .map( birth -> BirthResponse.builder()
                        .id( birth.getId() )
                        .name( birth.getName() )
                        .age( getAge( birth.getDateOfBirth() ) )
                        .birthday( getBirthday( birth.getDateOfBirth() ) )
                        .dateOfBirth( birth.getDateOfBirth() )
                        .createdDate( birth.getCreatedDate() )
                        .build() )
                .toList();
    }

    public void delete( String id ) {
        repository.deleteById( id );
    }

    public List< BirthResponse > findAllBirths() {
        return repository.findAll().stream()
                .sorted( Comparator.comparing( Birth :: getMonth )
                        .thenComparing( Birth :: getDay ) )
                .map( birth -> BirthResponse.builder()
                        .id( birth.getId() )
                        .name( birth.getName() )
                        .age( getAge( birth.getDateOfBirth() ) )
                        .birthday( getBirthday( birth.getDateOfBirth() ) )
                        .dateOfBirth( birth.getDateOfBirth() )
                        .createdDate( birth.getCreatedDate() )
                        .build() )
                .toList();
    }
}
