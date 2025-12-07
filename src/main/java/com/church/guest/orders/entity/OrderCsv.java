package com.church.guest.orders.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCsv {

    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "Pedido")
    private String numeroPedido;

    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "Nome")
    private String nomeCompleto;

    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "Congregacão")
    private String congregacao;

    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "Carg_Ministerial")
    private String cargoMinisterial;

    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "Funcão_EBD")
    private String funcaoEdb;

    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "Plenarias")
    private String plenarias;

    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "Apelido")
    private String apelido;

    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "Telefone")
    private String telefone;

    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "Produto")
    private String produto;

    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "Valor")
    private Double valorUnitario;

    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "Qtd")
    private Integer qtd;

    @CsvBindByPosition(position = 11)
    @CsvBindByName(column = "Total")
    private Double valorTotal;

    @CsvBindByPosition(position = 12)
    @CsvBindByName(column = "Pagamento")
    private String opcaoPagamento;

    @CsvBindByPosition(position = 13)
    @CsvBindByName(column = "Status")
    private String statusPagamento;

    @CsvBindByPosition(position = 14)
    @CsvBindByName(column = "Criado")
    private String createdDate;

}
