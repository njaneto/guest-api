package com.church.guest.orders.repository;

import com.church.guest.client.response.WhatsGwResponse;
import com.church.guest.orders.entity.Order;

public interface WhatsGwRepository {

    WhatsGwResponse send( Order order, String message );
}
