package com.church.guest.orders.service;

import com.church.guest.exceptions.GuestRuntimeException;
import com.church.guest.orders.entity.Order;
import com.church.guest.orders.entity.OrderCsv;
import com.church.guest.orders.mapper.OrdersMapper;
import com.church.guest.orders.pix.PixEmvBuilder;
import com.church.guest.orders.repository.OrdersRepository;
import com.church.guest.orders.web.dto.OrderCreateRequest;
import com.church.guest.orders.web.dto.OrderCreateResponse;
import com.church.guest.reception.utils.CsvUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class OrdersService {

    @Value( "${pix.key}" )
    private String key;

    @Value( "${pix.nome}" )
    private String nome;

    @Value( "${pix.cidade}" )
    private String cidade;


    private final OrdersRepository ordersRepository;
    private final NotificationService notificationService;

    @Autowired
    public OrdersService( OrdersRepository ordersRepository, NotificationService notificationService ) {
        this.ordersRepository = ordersRepository;
        this.notificationService = notificationService;
    }

    public OrderCreateResponse save( OrderCreateRequest request ) {

        var order = ordersRepository.save( OrdersMapper.toOrder( request ) );
        var response = getOrderCreateResponse( order );

        notificationService.newOrderNotification( order, response );

        return response;

    }

    private OrderCreateResponse getOrderCreateResponse( Order order ) {

        if( order.getOpcaoPagamento().equals( "CARTAO" ) ) {
            return OrderCreateResponse.builder()
                    .id( order.getId() )
                    .opcaoPagamento( order.getOpcaoPagamento() )
                    .paymentLink( OrdersMapper.toPaymentLink( order.getQtd() ) )
                    .build();
        }

        String payload = PixEmvBuilder.buildPayload(
                key, nome, cidade,
                order.getValorTotal(),
                order.getNumeroPedido().concat( "-dt:" )
                        .concat( LocalDate.now().toString() )
                        .concat( "-qtd:" )
                        .concat( order.getValorUnitario() ),
                order.getNumeroPedido()
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

    public List< Order > findAllByNumeroPedido(String numeroPedido) {
        return ordersRepository.findAllByNumeroPedido( numeroPedido );
    }

    public Order confirm( String id ) {

        AtomicReference< Order > orderAtomicReference = new AtomicReference<>();
        ordersRepository.findById( id )
                .ifPresentOrElse( g -> {
                    g.setStatusPagamento( "CONFIRMADO" );
                    orderAtomicReference.set( ordersRepository.save( g ) );
                }, () -> {
                    throw new GuestRuntimeException( "Pedido não localizado", HttpStatus.NOT_FOUND );
                } );

        var order = Optional.of( orderAtomicReference.get() ).get();
        notificationService.confirmOrderNotification( order );

        return order;

    }

    public Order cancel( String id ) {
        AtomicReference< Order > orderAtomicReference = new AtomicReference<>();
        ordersRepository.findById( id )
                .ifPresentOrElse( g -> {
                    g.setStatusPagamento( "CANCELADO" );
                    orderAtomicReference.set( ordersRepository.save( g ) );
                }, () -> {
                    throw new GuestRuntimeException( "Pedido não localizado", HttpStatus.NOT_FOUND );
                } );

        var order = Optional.of( orderAtomicReference.get() ).get();
        notificationService.cancelOrderNotification( order );

        return order;
    }


    public Order delivery( String id ) {

        AtomicReference< Order > orderAtomicReference = new AtomicReference<>();
        ordersRepository.findById( id )
                .ifPresentOrElse( g -> {
                    g.setStatusPagamento( "ENTREGUE" );
                    orderAtomicReference.set( ordersRepository.save( g ) );
                }, () -> {
                    throw new GuestRuntimeException( "Pedido não localizado", HttpStatus.NOT_FOUND );
                } );

        var order = Optional.of( orderAtomicReference.get() ).get();
        notificationService.deliveryOrderNotification( order );

        return order;

    }

    public void delete( String id ) {
        ordersRepository.deleteById( id );
    }


    public String notifyPendente() {

        AtomicInteger count = new AtomicInteger();
        ordersRepository.findAll().stream()
                .filter( orderDTO -> orderDTO.getStatusPagamento().equals( "PENDENTE" ) )
                .forEach( order -> {
                    count.getAndIncrement();
                    notificationService.pendenteOrderNotification( order, getOrderCreateResponse( order ) );
                });

        return count.get() + " pedido(s) pendente(s) notificado(s)";
    }

    @SneakyThrows
    public void exportOrderToCsv( HttpServletResponse response ) {

        PrintWriter writer = response.getWriter();
        writer.append( CsvUtils.buildHeader( OrderCsv.class ) );

        CsvUtils.writer( ordersRepository.findAll().stream()
                        .map( OrdersMapper :: toOrderCSV )
                        .toList()
                , writer );

    }
}
