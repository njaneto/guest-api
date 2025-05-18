package com.church.guest.repository;

import com.church.guest.domain.Events;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsRepository extends MongoRepository< Events, String> {

    List< Events> findByEventDateAfter( LocalDateTime localDateTime );
}
