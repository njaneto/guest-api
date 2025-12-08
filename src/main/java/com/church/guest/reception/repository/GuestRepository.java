package com.church.guest.reception.repository;

import com.church.guest.reception.entity.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GuestRepository extends MongoRepository<Guest, String> {

    List<Guest> findByCreatedDateAfterAndAnnouncedFalse(LocalDateTime startDate);
    List<Guest> findByCreatedDateAfter( LocalDateTime startDate );
}
