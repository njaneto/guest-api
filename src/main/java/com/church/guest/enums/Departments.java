package com.church.guest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Departments {

    ABDA( 0, "ABDA","ABDA (Coral e Orquestra)" ),
    BANDA( 1,"BANDA","Banda" ),
    CORAL_JOVEM(2, "CORAL JOVEM","Coral Jovem" ),
    DIACONOS( 3, "DIÁCONOS", "Diáconos" ),
    DISCIPULADOS( 4,"DISCIPULADOS","Discipulados" ),
    EBD_ESCOLA_BIBLICA_DOMINICAL( 5,"EBD","EBD Escola biblica dominical" ),
    GRUPO_AGAPE_CRIANCAS( 6, "GRUPO ÁGAPE","Grupo Ágape" ),
    GRUPO_DE_LOUVOR( 7, "GRUPO DE LOUVOR","Grupo de Louvor" ),
    GRUPO_SARA_IRMAS( 8, "GRUPO SARA","Grupo Sara" ),
    MIDIA( 9, "MÍDIA","Mídia" ),
    MINISTERIO_DA_FAMILIA_E_CASAIS( 10, "MINISTÉRIO DA FAMILIA","Ministério da Família & Casais" ),
    RECEPCAO_E_ACOMODACAO( 11, "RECEPÇÃO","Recepção & Acomodação" ),
    SEMEADO_MISSOES( 12, "SEMEADO","Semeado" ),
    SOM( 13, "SOM","Som" ),
    TOP_TEENS_ADOLESCENTES( 14, "TOP TEENS","Top Teens" ),
    ASILAR( 15, "ASILAR","Casa de idosos" ),
    HUMANITA( 16, "HUMANITA","Casa de recuperação" );

    private final Integer code;
    private final String department;
    private final String desc;

}
