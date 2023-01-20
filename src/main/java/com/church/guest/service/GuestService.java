package com.church.guest.service;

import com.church.guest.web.dto.GuestRequest;
import com.church.guest.domain.Guest;
import com.church.guest.mapper.GuestMapper;
import com.church.guest.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    @Autowired
    private GuestRepository repository;

    public Guest save(GuestRequest request) {
        return repository.save(GuestMapper.toGuest(request));
    }

    public Guest findGuestById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not fund"));
    }

    public List<Guest> findAll() {
        Calendar cal = Calendar.getInstance();
        ;
        cal.add(Calendar.HOUR, -5);

        return repository.findByCreatedDateAfter(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .stream()
                .sorted(Comparator.comparing(Guest::getGuestType))
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public Guest editGuest(String id, GuestRequest request) {

        checkGuestExist(id);

        Guest guest = GuestMapper.toGuest(request);
        guest.setId(id);

        return repository.save(guest);
    }

    private void checkGuestExist(String id) {
        if (repository.findById(id).isEmpty()) {
            throw new RuntimeException("Guest not fund");
        }
    }
}
