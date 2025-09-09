package com.church.guest.orders.web;

import com.church.guest.orders.entity.Order;
import com.church.guest.orders.mapper.OrdersMapper;
import com.church.guest.orders.service.OrdersService;
import com.church.guest.orders.web.dto.OrderCreateRequest;
import com.church.guest.orders.web.dto.OrderCreateResponse;
import com.church.guest.orders.web.dto.OrderDTO;
import com.church.guest.orders.web.dto.OrdersResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public OrderCreateResponse saveGuest( @Valid @RequestBody OrderCreateRequest request ) {
        return service.save( request );
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

    @PutMapping( value = "/{id}/confirm" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public OrderDTO confirm( @Valid @PathVariable( name = "id" ) String id ) {
        return OrdersMapper.toOrderDTO( service.confirm( id ) );
    }

    @PutMapping( value = "/{id}/cancel" )
    @ResponseStatus( value = HttpStatus.OK )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public OrderDTO cancel( @Valid @PathVariable( name = "id" ) String id ) {
        return OrdersMapper.toOrderDTO( service.cancel( id ) );
    }

    @DeleteMapping( value = "/{id}/delete")
    @ResponseStatus( value = HttpStatus.NO_CONTENT )
    @Secured( "CJ_ROLE_USER_WRITER" )
    public void delete( @Valid @PathVariable( name = "id" ) String id ) {
        service.delete( id );
    }


}
