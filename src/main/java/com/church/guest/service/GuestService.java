package com.church.guest.service;

import com.church.guest.entity.Guest;
import com.church.guest.entity.Sector;
import com.church.guest.exceptions.GuestRuntimeException;
import com.church.guest.mapper.GuestMapper;
import com.church.guest.repository.GuestRepository;
import com.church.guest.repository.SectorRepository;
import com.church.guest.utils.CsvUtils;
import com.church.guest.entity.GuestCsv;
import com.church.guest.web.dto.GuestRequest;
import com.church.guest.web.dto.SectorRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    private final SectorRepository sectorRepository;

    @Autowired
    public GuestService( GuestRepository guestRepository, SectorRepository sectorRepository ) {
        this.guestRepository = guestRepository;
        this.sectorRepository = sectorRepository;
    }

    public Guest save( GuestRequest request ) {
        return guestRepository.save( GuestMapper.toGuest( request ) );
    }

    public Sector saveSector( SectorRequest request ) {
        sectorRepository.findByValue( request.getValue() )
                .ifPresent( sector -> {
                    throw new GuestRuntimeException( "Setor já registrado para: " + sector.getValue(), HttpStatus.CONFLICT );
                } );

        return sectorRepository.save( Sector.builder()
                .value( request.getValue() )
                .build() );
    }

    public Guest findGuestById( String id ) {
        AtomicReference< Guest > guest = new AtomicReference<>();

        guestRepository.findById( id )
                .ifPresentOrElse( guest :: set, () -> {
                    throw new GuestRuntimeException( "Aviso não localizado", HttpStatus.NOT_FOUND );
                } );

        return Optional.of( guest.get() ).get();
    }

    public List< Guest > findAllAnnouncedFalse() {
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.HOUR, -5 );

        return guestRepository.findByCreatedDateAfterAndAnnouncedFalse( cal.getTime().toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime() )
                .stream()
                .sorted( Comparator.comparing( guest -> guest.getGuestType().getSort() ) )
                .toList();
    }

    public List< Guest > findAllDayHistory() {
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.HOUR, -5 );

        return guestRepository.findByCreatedDateAfter( cal.getTime().toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime() )
                .stream()
                .sorted( Comparator.comparing( Guest :: getGuestType ) )
                .toList();
    }

    public void delete( String id ) {
        guestRepository.deleteById( id );
    }

    public Guest editGuest( String id, GuestRequest request ) {

        Guest guest = GuestMapper.toGuest( request );

        guestRepository.findById( id )
                .ifPresentOrElse( g -> guest.setId( id ), () -> {
                    throw new GuestRuntimeException( "Aviso não localizado", HttpStatus.NOT_FOUND );
                } );

        return guestRepository.save( guest );
    }

    public Guest announcedGuest( String id, Boolean read ) {

        AtomicReference< Guest > guest = new AtomicReference<>();
        guestRepository.findById( id )
                .ifPresentOrElse( g -> {
                    g.setAnnounced( read );
                    guest.set( guestRepository.save( g ) );
                }, () -> {
                    throw new GuestRuntimeException( "Aviso não localizado", HttpStatus.NOT_FOUND );
                } );

        return Optional.of( guest.get() ).get();
    }

    public List< Guest > fetchAll() {
        return guestRepository.findAll();
    }

    @SneakyThrows
    public void exportGuestToCsv( HttpServletResponse response ) {

        PrintWriter writer = response.getWriter();
        writer.append( CsvUtils.buildHeader( GuestCsv.class ) );

        CsvUtils.writer( fetchAll().stream()
                        .map( GuestMapper :: toGuestCsv )
                        .toList()
                , writer );

    }

    public List< Sector > findAllSectors() {
        return sectorRepository.findAll();
    }
}
