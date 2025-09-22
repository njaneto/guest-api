package com.church.guest.orders.mapper;

import com.church.guest.orders.entity.Order;
import com.church.guest.orders.entity.OrderCsv;
import com.church.guest.orders.web.dto.OrderCreateRequest;
import com.church.guest.orders.web.dto.OrderDTO;
import com.church.guest.orders.web.dto.OrdersResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class OrdersMapper {

    public static OrdersResponse toOrderResponses( List< OrderDTO > responses ) {
        return OrdersResponse.builder()
                .orders( responses )
                .valorTotalConfirmado( responses.stream()
                        .filter( orderDTO -> ( orderDTO.getStatusPagamento().equals( "CONFIRMADO" ) || orderDTO.getStatusPagamento().equals( "ENTREGUE" ) ) )
                        .mapToDouble( value -> Double.parseDouble( value.getValorTotal() ) )
                        .sum()
                )
                .valorTotalPendente( responses.stream()
                        .filter( orderDTO -> orderDTO.getStatusPagamento().equals( "PENDENTE" ) )
                        .mapToDouble( value -> Double.parseDouble( value.getValorTotal() ) )
                        .sum()
                )
                .size( responses.size() )
                .build();
    }

    public static OrderDTO toOrderDTO( Order order ) {
        return OrderDTO.builder()
                .id( order.getId() )
                .numeroPedido( order.getNumeroPedido() )
                .cpf( order.getCpf() )
                .nomeCompleto( order.getNomeCompleto() )
                .apelido( order.getApelido() )
                .email( order.getEmail() )
                .telefone( order.getTelefone() )
                .produto( order.getProduto() )
                .qtd( order.getQtd() )
                .opcaoPagamento( order.getOpcaoPagamento() )
                .statusPagamento( order.getStatusPagamento() )
                .valorUnitario( order.getValorUnitario() )
                .valorTotal( order.getValorTotal() )
                .createdDate( order.getCreatedDate() )
                .build();
    }

    public static Order toOrder( OrderCreateRequest request ) {

        return Order.builder()
                .numeroPedido( "CJ".concat( StringUtils.leftPad( String.valueOf( new SecureRandom().nextInt( 999999 ) ), 6, '0' ) ) )
                .cpf( request.getCpf() )
                .nomeCompleto( request.getNomeCompleto() )
                .apelido( request.getApelido() )
                .email( request.getEmail() )
                .telefone( request.getTelefone().replaceAll( "\\D", "" ) )
                .produto( request.getProduto() )
                .qtd( request.getQtd() )
                .opcaoPagamento( request.getOpcaoPagamento() )
                .statusPagamento( "PENDENTE" )
                .valorUnitario( request.getValorUnitario() )
                .valorTotal( request.getValorTotal() )
                .build();
    }

    public static String toPaymentLink( int qtd ) {

        if( qtd == 1 ) {
            return "https://payment-link-v3.stone.com.br/pl_Wnv3KB1XroLwgnvHnIV5gx2VAba0O8Z6";
        } else if( qtd == 2 ) {
            return "https://payment-link-v3.stone.com.br/pl_qNxJpBegDrWvY5WeujIzV2K0X9jPamlO";
        } else if( qtd == 3 ) {
            return "https://payment-link-v3.stone.com.br/pl_o5bYQXvEA79R3WYC8FBlJDkBemly1zqK";
        } else if( qtd == 4 ) {
            return "https://payment-link-v3.stone.com.br/pl_zm5Kk9yWADbMLzCoAiw203xOBN4JrlQo";
        } else if( qtd == 5 ) {
            return "https://payment-link-v3.stone.com.br/pl_kOnDLJZ6BmXyMATM1spey02PKaQv1jl7";
        } else if( qtd == 6 ) {
            return "https://payment-link-v3.stone.com.br/pl_PqKxLepNjV53VVYT6ijw34JRoEnw6MGy";
        } else if( qtd == 7 ) {
            return "https://payment-link-v3.stone.com.br/pl_knjGaXYA4Q6K70tr4C42l3N8vprWVbPe";
        } else if( qtd == 8 ) {
            return "https://payment-link-v3.stone.com.br/pl_vqn6g3m27aoQeLEI9S3NMdV0EZAxKp8R";
        } else if( qtd == 9 ) {
            return "https://payment-link-v3.stone.com.br/pl_X9MPORkYVbay1ZXHjf7Dz4LjB7eg2nZN";
        } else if( qtd == 10 ) {
            return "https://payment-link-v3.stone.com.br/pl_2gMz7Y0GldqberziBRTzEnV6yEvwJQW4";
        }
        return "https://payment-link-v3.stone.com.br/pl_Wnv3KB1XroLwgnvHnIV5gx2VAba0O8Z6";
    }

    public static String getNameOrApelido( String nomeCompleto, String apelido ) {

        return StringUtils.isNotBlank( apelido ) ? apelido.stripTrailing() : nomeCompleto.stripTrailing();
    }

    public static OrderCsv toOrderCSV( Order order ) {
        return OrderCsv.builder()
                .numeroPedido( order.getNumeroPedido() )
                .nomeCompleto( order.getNomeCompleto() )
                .apelido( order.getApelido() )
                .telefone( order.getTelefone() )
                .produto( order.getProduto() )
                .qtd( order.getQtd() )
                .opcaoPagamento( order.getOpcaoPagamento() )
                .statusPagamento( order.getStatusPagamento() )
                .valorUnitario( Double.valueOf( order.getValorUnitario() ) )
                .qtd( order.getQtd() )
                .valorTotal( Double.valueOf( order.getValorTotal() ) )
                .createdDate( order.getCreatedDate().format( DateTimeFormatter.ofPattern( "dd-MM-yyyy HH:mm:ss" ) ) )
                .build();
    }
}
