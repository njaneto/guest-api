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
    @CsvBindByName(column = "Apelido")
    private String apelido;

    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "Telefone")
    private String telefone;

    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "Produto")
    private String produto;

    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "Valor")
    private Double valorUnitario;

    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "Qtd")
    private Integer qtd;

    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "Total")
    private Double valorTotal;

    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "pagamento")
    private String opcaoPagamento;

    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "Status")
    private String statusPagamento;

    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "Criado")
    private String createdDate;

}
