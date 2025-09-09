package com.church.guest.orders.mapper;

import com.church.guest.orders.entity.Order;
import com.church.guest.orders.web.dto.OrderCreateRequest;
import com.church.guest.orders.web.dto.OrderCreateResponse;
import com.church.guest.orders.web.dto.OrderDTO;
import com.church.guest.orders.web.dto.OrdersResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdersMapper {
    public static OrderCreateResponse toOrderCreateResponse( Order order ) {
        return null;
    }

    public static OrderCreateResponse toOrderCreateResponses( List< OrderCreateResponse > responses ) {
        return null;
    }

    public static OrdersResponse toOrderResponses( List< OrderDTO > responses ) {
        return OrdersResponse.builder()
                .orders( responses )
                .size( responses.size() )
                .build();
    }

    public static OrderDTO toOrderDTO( Order order ) {
        return OrderDTO.builder()
                .id( order.getId() )
                .cpf( order.getCpf() )
                .nomeCompleto( order.getNomeCompleto() )
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
                .cpf( request.getCpf() )
                .nomeCompleto( request.getNomeCompleto() )
                .email( request.getEmail() )
                .telefone( request.getTelefone() )
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
            return "";
        } else if( qtd == 3 ) {
            return "";
        } else if( qtd == 4 ) {
            return "";
        } else if( qtd == 5 ) {
            return "";
        } else if( qtd == 6 ) {
            return "";
        } else if( qtd == 7 ) {
            return "";
        } else if( qtd == 8 ) {
            return "";
        } else if( qtd == 9 ) {
            return "";
        } else if( qtd == 10 ) {
            return "";
        }

        return "https://payment-link-v3.stone.com.br/pl_Wnv3KB1XroLwgnvHnIV5gx2VAba0O8Z6";
    }
}
