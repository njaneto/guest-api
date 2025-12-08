package com.church.guest.reception.repository;

import com.church.guest.reception.entity.Birth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BirthdayRepository extends MongoRepository< Birth, String > {

    List< Birth > findByMonthAndDayBetween( int currentMonth, int startDay, int currentDay );

    Optional< Birth > findByNameAndDateOfBirth( String name, LocalDate dateOfBirth );
}
