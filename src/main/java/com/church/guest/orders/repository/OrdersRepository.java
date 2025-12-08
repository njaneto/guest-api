package com.church.guest.orders.repository;

import com.church.guest.orders.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends MongoRepository< Order, String > {

    List< Order > findAllByNumeroPedido( String numeroPedido );

}
