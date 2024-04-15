package com.church.guest.service;

import com.church.guest.domain.Guest;
import com.church.guest.mapper.GuestMapper;
import com.church.guest.repository.GuestRepository;
import com.church.guest.util.CsvUtil;
import com.church.guest.web.dto.GuestCsv;
import com.church.guest.web.dto.GuestRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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
        cal.add(Calendar.HOUR, -5);

        return repository.findByCreatedDateAfterAndAnnouncedFalse(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
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

    public Guest announcedGuest(String id) {

        checkGuestExist(id);

        Guest guest = repository.findById(id).orElseThrow();
        guest.setAnnounced(Boolean.TRUE);

        return repository.save(guest);

    }

    public List<Guest> fetchAllBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByCreatedDateBetween(startDate, endDate);
    }

    @SneakyThrows
    public void exportGuestToCsv(HttpServletResponse response, LocalDateTime startDate, LocalDateTime endDate) {

        PrintWriter writer = response.getWriter();
        writer.append(CsvUtil.buildHeader(GuestCsv.class));

        CsvUtil.writer(fetchAllBetween(startDate, endDate).stream()
                .map(GuestMapper::toGuestCsv)
                .collect(Collectors.toList())
                , writer);

    }
}
