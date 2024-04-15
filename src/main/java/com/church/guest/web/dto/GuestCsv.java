package com.church.guest.web.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestCsv {

    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "id")
    private String id;

    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "data_criacao")
    private String createdDate;

    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "tipo_aviso")
    private String guestType;

    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "nome")
    private String name;

    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "convidado_por")
    private String invitedBy;

    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "frenquenta_alguma_igreja")
    private Boolean attend;

    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "nome_igreja")
    private String churchName;

    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "tipo_aniversario")
    private String birthdayType;

    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "idade")
    private String age;

    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "de")
    private String to;

    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "para")
    private String from;

    @CsvBindByPosition(position = 11)
    @CsvBindByName(column = "nome_pais")
    private String parents;

    @CsvBindByPosition(position = 12)
    @CsvBindByName(column = "nome_filho")
    private String children;

    @CsvBindByPosition(position = 13)
    @CsvBindByName(column = "mensagem")
    private String message;

    @CsvBindByPosition(position = 14)
    @CsvBindByName(column = "lido")
    private Boolean announced;

}
