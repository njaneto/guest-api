package com.church.guest.service;

import com.church.guest.domain.Guest;
import com.church.guest.domain.Sector;
import com.church.guest.mapper.GuestMapper;
import com.church.guest.repository.GuestRepository;
import com.church.guest.repository.SectorRepository;
import com.church.guest.util.CsvUtil;
import com.church.guest.web.dto.GuestCsv;
import com.church.guest.web.dto.GuestRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private SectorRepository sectorRepository;

    public Guest save( GuestRequest request ) {
        return guestRepository.save( GuestMapper.toGuest( request ) );
    }

    public Sector saveSector( Sector sector ) {
        return sectorRepository.save( sector );
    }

    public Guest findGuestById( String id ) {
        return guestRepository.findById( id )
                .orElseThrow( () -> new RuntimeException( "Guest not fund" ) );
    }

    public List< Guest > findAllAnnouncedFalse() {
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.HOUR, -5 );

        return guestRepository.findByCreatedDateAfterAndAnnouncedFalse( cal.getTime().toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime() )
                .stream()
                .sorted( Comparator.comparing( guest -> guest.getGuestType().getSort() ) )
                .collect( Collectors.toList() );
    }

    public List< Guest > findAllDayHistory() {
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.HOUR, -5 );

        return guestRepository.findByCreatedDateAfter( cal.getTime().toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime() )
                .stream()
                .sorted( Comparator.comparing( Guest :: getGuestType ) )
                .collect( Collectors.toList() );
    }

    public void delete( String id ) {
        guestRepository.deleteById( id );
    }

    public Guest editGuest( String id, GuestRequest request ) {

        checkGuestExist( id );

        Guest guest = GuestMapper.toGuest( request );
        guest.setId( id );

        return guestRepository.save( guest );
    }

    private void checkGuestExist( String id ) {
        if( guestRepository.findById( id ).isEmpty() ) {
            throw new RuntimeException( "Guest not fund" );
        }
    }

    public Guest announcedGuest( String id, Boolean read ) {

        checkGuestExist( id );

        Guest guest = guestRepository.findById( id ).orElseThrow();
        guest.setAnnounced( read );

        return guestRepository.save( guest );

    }

    public List< Guest > fetchAll() {
        return guestRepository.findAll();
    }

    @SneakyThrows
    public void exportGuestToCsv( HttpServletResponse response ) {

        PrintWriter writer = response.getWriter();
        writer.append( CsvUtil.buildHeader( GuestCsv.class ) );

        CsvUtil.writer( fetchAll().stream()
                        .map( GuestMapper :: toGuestCsv )
                        .collect( Collectors.toList() )
                , writer );

    }

    public List< Sector > findAllSectors() {
        return sectorRepository.findAll();
    }
}
