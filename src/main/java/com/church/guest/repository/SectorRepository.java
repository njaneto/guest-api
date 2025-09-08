package com.church.guest.repository;

import com.church.guest.entity.Sector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorRepository extends MongoRepository< Sector, String > {

    Optional< Sector > findByValue( String value );
}
