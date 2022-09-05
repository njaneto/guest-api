package com.church.guest.service;

import com.church.guest.domain.Guest;
import com.church.guest.enums.VisitPeriod;
import com.church.guest.mapper.GuestMapper;
import com.church.guest.repository.GuestRepository;
import com.church.guest.web.dto.GuestRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    @Autowired
    private GuestRepository repository;

    public Guest save(GuestRequest request ){
        return repository.save(GuestMapper.toGuest(request));
    }

    public List<Guest> findAll(VisitPeriod visitPeriod){
        return repository.findByVisitPeriodAndCreatedDateLessThan( visitPeriod, LocalDateTime.now() )
                .stream()
                .sorted( Comparator.comparing( Guest :: getGuestType ) )
                .collect(Collectors.toList());
    }

}
