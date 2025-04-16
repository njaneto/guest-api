package com.church.guest.repository;

import com.church.guest.domain.Sector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends MongoRepository< Sector, String> {

}
