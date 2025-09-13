package com.church.guest.orders.repository.impl;

import com.church.guest.client.WhatsGwClient;
import com.church.guest.client.request.WhatsGwRequest;
import com.church.guest.client.response.WhatsGwResponse;
import com.church.guest.orders.entity.Order;
import com.church.guest.orders.repository.WhatsGwRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class WhatsGwImpl implements WhatsGwRepository {

    @Value( "${notification.whatsGw.apikey}" )
    private String apikey;

    @Value( "${notification.whatsGw.phone_number}" )
    private String phoneNumber;

    private final WhatsGwClient whatsGwClient;

    @Autowired
    public WhatsGwImpl( WhatsGwClient whatsGwClient ) {
        this.whatsGwClient = whatsGwClient;
    }

    @Override
    public WhatsGwResponse send( Order order, String message ) {

        return whatsGwClient.send( WhatsGwRequest.builder()
                .apikey( apikey )
                .phone_number( phoneNumber )
                .check_status( "1" )
                .message_type( "text" )
                .message_custom_id( order.getNumeroPedido() )
                .contact_phone_number( "55".concat( order.getTelefone().trim() ) )
                .message_body( message )
                .build() );
    }
}
