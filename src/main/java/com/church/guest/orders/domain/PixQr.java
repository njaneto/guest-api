package com.church.guest.orders.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PixQr {
    private String chave;      // sua chave Pix
    private String nome;       // recebedor (até 25 chars)
    private String cidade;     // (até 15 chars, sem acentos)
    private String valor;      // ex "49.90" (opcional)
    private String descricao;  // opcional
    private String txid;       // opcional (<= 35)
    private Integer size;      // opcional (px), default 320
}
