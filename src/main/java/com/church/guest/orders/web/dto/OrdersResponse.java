package com.church.guest.orders.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrdersResponse {
    
    private List< OrderDTO > orders;
    private Double valorTotalConfirmado;
    private Double valorTotalPendente;
    private Integer size;
}
