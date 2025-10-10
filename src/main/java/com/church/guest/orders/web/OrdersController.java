package com.church.guest.orders.web;

import com.church.guest.orders.entity.Order;
import com.church.guest.orders.mapper.OrdersMapper;
import com.church.guest.orders.service.OrdersService;
import com.church.guest.orders.web.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Validated
@RestController
@CrossOrigin( value = "*" )
@RequestMapping( "/cj/orders" )
public class OrdersController {

    private final OrdersService service;

    @Autowired
    public OrdersController( OrdersService service) {
        this.service = service;
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.CREATED )
    public OrderCreateResponse save( @Valid @RequestBody OrderCreateRequest request ) {
        return service.save( request );
    }

    @PostMapping( value = "/notify" )
    @ResponseStatus( value = HttpStatus.ACCEPTED )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public NotifyDTO notifyPendente() {
        return NotifyDTO.builder().message( service.notifyPendente() ).build();
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_READ" )
    public OrdersResponse findAllOrders() {

        final List< OrderDTO > responses = service.findAll()
                .stream()
                .sorted( Comparator.comparing( Order ::getStatusPagamento ).reversed())
                .map( OrdersMapper :: toOrderDTO )
                .toList();

        return OrdersMapper.toOrderResponses( responses );
    }

    @GetMapping( value = "/{numeroPedido}/order", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_READ" )
    public OrdersResponse findAllOrdersByOrder(@PathVariable String numeroPedido) {

        final List< OrderDTO > responses = service.findAllByNumeroPedido(numeroPedido)
                .stream()
                .sorted( Comparator.comparing( Order ::getStatusPagamento ).reversed())
                .map( OrdersMapper :: toOrderDTO )
                .toList();

        return OrdersMapper.toOrderResponses( responses );
    }

    @PutMapping( value = "/{id}/confirm" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public OrderDTO confirm( @Valid @PathVariable( name = "id" ) String id ) {
        return OrdersMapper.toOrderDTO( service.confirm( id ) );
    }

    @PutMapping( value = "/{id}/delivery" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public OrderDTO delivery( @Valid @PathVariable( name = "id" ) String id ) {
        return OrdersMapper.toOrderDTO( service.delivery( id ) );
    }

    @PutMapping( value = "/{id}/cancel" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public OrderDTO cancel( @Valid @PathVariable( name = "id" ) String id ) {
        return OrdersMapper.toOrderDTO( service.cancel( id ) );
    }

    @PutMapping( value = "/{id}/pendent" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public OrderDTO pendent( @Valid @PathVariable( name = "id" ) String id ) {
        return OrdersMapper.toOrderDTO( service.pendent( id ) );
    }

    @DeleteMapping( value = "/{id}/delete")
    @ResponseStatus( value = HttpStatus.NO_CONTENT )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public void delete( @Valid @PathVariable( name = "id" ) String id ) {
        service.delete( id );
    }


    @GetMapping( value = "/export", produces = "text/csv" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public void exportOrderToCsv( HttpServletResponse response ) {

        response.setHeader( HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=".concat( "order-".concat( LocalDateTime.now().toString() ) ) );
        response.setContentType( "text/csv; charset=UTF-8" );
        service.exportOrderToCsv( response );

    }

}
