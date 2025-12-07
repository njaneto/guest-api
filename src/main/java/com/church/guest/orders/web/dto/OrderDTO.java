package com.church.guest.orders.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderDTO {

    private String id;
    private String numeroPedido;
    private String cpf;
    private LocalDateTime createdDate;
    private String nomeCompleto;

    private String congregacao;
    private String cargoMinisterial;
    private String funcaoEdb;
    private String plenarias;

    private String apelido;
    private String email;
    private String telefone;
    private String produto;
    private int qtd;
    private String statusPagamento;
    private String opcaoPagamento;
    private String valorUnitario;
    private String valorTotal;
}
