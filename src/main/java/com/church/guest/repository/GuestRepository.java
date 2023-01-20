package com.church.guest.repository;

import com.church.guest.domain.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public interface GuestRepository extends MongoRepository<Guest, String> {

    List<Guest> findByCreatedDateAfter(LocalDateTime startDate);

}
