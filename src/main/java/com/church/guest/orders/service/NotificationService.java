package com.church.guest.orders.service;

import com.church.guest.exceptions.GuestRuntimeException;
import com.church.guest.orders.entity.Order;
import com.church.guest.orders.mapper.OrdersMapper;
import com.church.guest.orders.repository.impl.WhatsGwImpl;
import com.church.guest.orders.web.dto.OrderCreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @Value( "${notification.whatsGw.adm_phone_number}" )
    private String admPhoneNumber;

    private final WhatsGwImpl whatsGwImpl;

    @Autowired
    public NotificationService( WhatsGwImpl whatsGwImpl ) {
        this.whatsGwImpl = whatsGwImpl;
    }

    public void newOrderNotification( Order order, OrderCreateResponse orderCreateResponse ) {

        var complemento = "";
        if( orderCreateResponse.getOpcaoPagamento().equals( "CARTAO" ) ) {
            complemento = "Opa! Para facilitar, deixo aqui o link de pagamento da sua compra: \n ".concat( orderCreateResponse.getPaymentLink() );
        } else {
            complemento = "Opa! Para facilitar, deixo aqui o PIX Copia e Cola de pagamento da sua compra: \n ".concat( orderCreateResponse.getCopiaECola() );
        }

        var msg = new StringBuilder()
                .append( "Ola " )
                .append( "_" )
                .append( OrdersMapper.getNameOrApelido( order.getNomeCompleto(), order.getApelido() ) )
                .append( "_" )
                .append( " Seu Pedido " )
                .append( "*" )
                .append( order.getNumeroPedido() )
                .append( "*" )
                .append( " foi registrado com sucesso. \n" )
                .append( "Para que possamos confirmar seu pagamento mais rapidamente, envie o comprovante *junto com o numero do pedido* para o número abaixo. \uD83D\uDE0A" )
                .append( "\n*" )
                .append( admPhoneNumber )
                .append( "*\n" )
                .append( "\n\n\n" )
                .append( complemento );

        try {

            var response = whatsGwImpl.send( order, msg.toString() );
            log.info( response.toString() );
        } catch( Exception e ) {
            log.error( e.getMessage() );
            throw new GuestRuntimeException( "Menesagem nao enviada", HttpStatus.BAD_REQUEST );
        }

    }

    public void confirmOrderNotification( Order order ) {

        var msg = new StringBuilder()
                .append( "Ola " )
                .append( "_" )
                .append( OrdersMapper.getNameOrApelido( order.getNomeCompleto(), order.getApelido() ) )
                .append( "_" )
                .append( " Seu Pedido " )
                .append( "*" )
                .append( order.getNumeroPedido() )
                .append( "*" )
                .append( " foi *CONFIRMADO* com sucesso. \uD83E\uDD73" );
        try {

            var response = whatsGwImpl.send( order, msg.toString() );
            log.info( response.toString() );

        } catch( Exception e ) {
            log.error( e.getMessage() );
            throw new GuestRuntimeException( "Menesagem nao enviada", HttpStatus.BAD_REQUEST );
        }

    }

    public void cancelOrderNotification( Order order ) {

        var msg = new StringBuilder()
                .append( "Ola " )
                .append( "_" )
                .append( OrdersMapper.getNameOrApelido( order.getNomeCompleto(), order.getApelido() ) )
                .append( "_" )
                .append( " Seu Pedido " )
                .append( "*" )
                .append( order.getNumeroPedido() )
                .append( "*" )
                .append( " foi *CANCELADO* com sucesso. \uD83D\uDE22" );
        try {

            var response = whatsGwImpl.send( order, msg.toString() );
            log.info( response.toString() );

        } catch( Exception e ) {
            log.error( e.getMessage() );
            throw new GuestRuntimeException( "Menesagem nao enviada", HttpStatus.BAD_REQUEST );
        }

    }

}
