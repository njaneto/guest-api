package com.church.guest.repository;

import com.church.guest.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventsRepository extends MongoRepository< Event, String> {

    List< Event > findByEventDateAfter( LocalDateTime localDateTime );
}
