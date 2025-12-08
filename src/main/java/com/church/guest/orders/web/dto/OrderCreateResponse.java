package com.church.guest.orders.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreateResponse {

    private String id;
    private String opcaoPagamento;
    private String qrCodeUrl;
    private String copiaECola;
    private String paymentLink;


}
