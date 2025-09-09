package com.church.guest.orders.service;

import com.church.guest.exceptions.GuestRuntimeException;
import com.church.guest.orders.entity.Order;
import com.church.guest.orders.mapper.OrdersMapper;
import com.church.guest.orders.pix.PixEmvBuilder;
import com.church.guest.orders.repository.OrdersRepository;
import com.church.guest.orders.web.dto.OrderCreateRequest;
import com.church.guest.orders.web.dto.OrderCreateResponse;
import com.church.guest.reception.entity.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService( OrdersRepository ordersRepository ) {
        this.ordersRepository = ordersRepository;
    }

    public OrderCreateResponse save( OrderCreateRequest request ) {

        var order = ordersRepository.save( OrdersMapper.toOrder( request ) );

        if( request.getOpcaoPagamento().equals( "CARTAO" ) ) {
            return OrderCreateResponse.builder()
                    .id( order.getId() )
                    .opcaoPagamento( order.getOpcaoPagamento() )
                    .paymentLink( OrdersMapper.toPaymentLink( request.getQtd() ) )
                    .build();
        }

        String payload = PixEmvBuilder.buildPayload(
                "39604280813", "Nilo Jose de Andrade Neto", "Osasco",
                request.getValorTotal(),
                request.getCpf().concat( "-dt:" )
                        .concat( LocalDate.now().toString() )
                        .concat( "-qtd:" )
                        .concat( request.getValorUnitario() ),
                ""
        );

        String base64 = QrService.toPngBase64( payload, 320 );

        return OrderCreateResponse.builder()
                .id( order.getId() )
                .opcaoPagamento( order.getOpcaoPagamento() )
                .copiaECola( payload )
                .qrCodeUrl( "data:image/png;base64," + base64 )
                .build();

    }

    public List< Order > findAll() {
        return ordersRepository.findAll();
    }

    public Order confirm( String id ) {

        AtomicReference< Order > order = new AtomicReference<>();
        ordersRepository.findById( id )
                .ifPresentOrElse( g -> {
                    g.setStatusPagamento( "CONFIRMADO" );
                    order.set( ordersRepository.save( g ) );
                }, () -> {
                    throw new GuestRuntimeException( "Pedido não localizado", HttpStatus.NOT_FOUND );
                } );

        return Optional.of( order.get() ).get();

    }

    public Order cancel( String id ) {
        AtomicReference< Order > order = new AtomicReference<>();
        ordersRepository.findById( id )
                .ifPresentOrElse( g -> {
                    g.setStatusPagamento( "CANCELADO" );
                    order.set( ordersRepository.save( g ) );
                }, () -> {
                    throw new GuestRuntimeException( "Pedido não localizado", HttpStatus.NOT_FOUND );
                } );

        return Optional.of( order.get() ).get();
    }

    public void delete( String id ) {
        ordersRepository.deleteById( id );
    }
}
