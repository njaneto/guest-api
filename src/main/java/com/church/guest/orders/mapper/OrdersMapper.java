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
                .cargoMinisterial( order.getCargoMinisterial() )
                .funcaoEdb( order.getFuncaoEdb() )
                .plenarias( order.getPlenarias() )
                .congregacao( order.getCongregacao() )
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
                .numeroPedido( request.getPrefix().concat( StringUtils.leftPad( String.valueOf( new SecureRandom().nextInt( 999999 ) ), 6, '0' ) ) )
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
                .congregacao( request.getCongregacao() )
                .funcaoEdb( request.getFuncaoEdb() )
                .plenarias( request.getPlenarias() )
                .cargoMinisterial( request.getCargoMinisterial() )
                .build();
    }

    public static String getNameOrApelido( String nomeCompleto, String apelido ) {

        return StringUtils.isNotBlank( apelido ) ? apelido.stripTrailing() : nomeCompleto.stripTrailing();
    }

    public static OrderCsv toOrderCSV( Order order ) {
        return OrderCsv.builder()
                .numeroPedido( order.getNumeroPedido() )
                .nomeCompleto( order.getNomeCompleto() )
                .congregacao( order.getCongregacao() )
                .funcaoEdb( order.getFuncaoEdb() )
                .plenarias( order.getPlenarias() )
                .cargoMinisterial( order.getCargoMinisterial() )
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
