package com.church.guest.orders.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    private String cpf;

    @NotBlank
    private String nomeCompleto;

    private String apelido;
    private String email;

    @NotBlank
    private String telefone;

    @NotBlank
    private String produto;

    @NotNull
    private int qtd;

    @NotBlank
    private String opcaoPagamento;

    @NotNull
    private String valorUnitario;

    @NotNull
    private String valorTotal;

}
