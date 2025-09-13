package com.church.guest.orders.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Order {

    @Id
    private String id;

    @CreatedDate()
    private LocalDateTime createdDate;

    private String numeroPedido;
    private String cpf;
    private String nomeCompleto;
    private String apelido;
    private String email;
    private String telefone;
    private String produto;
    private int qtd;
    private String opcaoPagamento;
    private String statusPagamento;
    private String valorUnitario;
    private String valorTotal;

}
