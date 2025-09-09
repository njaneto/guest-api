package com.church.guest.orders.pix;

import java.text.Normalizer;

public class PixEmvBuilder {

    private static String tlv(String id, String value) {
        if (value == null) value = "";
        String v = value;
        String len = String.format("%02d", v.length());
        return id + len + v;
    }

    private static String normalizeUpperAscii(String s, int maxLen) {
        if (s == null) return "";
        String noAccents = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String ascii = noAccents.replaceAll("[^A-Za-z0-9 \\-\\.]", "");
        String up = ascii.toUpperCase();
        return up.substring(0, Math.min(maxLen, up.length()));
    }

    public static String buildPayload(String chave, String nome, String cidade,
                                      String valor /* null = sem valor */,
                                      String descricao /* opcional */,
                                      String txid /* opcional */) {
        // 00 e 01
        String id00 = tlv("00", "01");
        // 11 (sem valor) ou 12 (com valor). Ambos funcionam; use 12 se fixar valor.
        String id01 = tlv("01", valor != null && !valor.isBlank() ? "12" : "11");

        // 26 – Merchant Account Information
        String gui = tlv("00", "br.gov.bcb.pix");
        String chaveTlv = tlv("01", chave);
        String descr = (descricao != null && !descricao.isBlank()) ? tlv("02", descricao) : "";
        String id26 = tlv("26", gui + chaveTlv + descr);

        // 52, 53, 54, 58, 59, 60
        String id52 = tlv("52", "0000");     // MCC default
        String id53 = tlv("53", "986");      // BRL
        String id54 = (valor != null && !valor.isBlank())
                ? tlv("54", String.format(java.util.Locale.US, "%.2f", Double.parseDouble(valor)))
                : "";
        String id58 = tlv("58", "BR");
        String id59 = tlv("59", normalizeUpperAscii(nome, 25));
        String id60 = tlv("60", normalizeUpperAscii(cidade, 15));

        // 62 – Additional Data (TXID até 35)
        String tx = (txid == null || txid.isBlank()) ? ("TX" + System.currentTimeMillis()) : txid;
        String id6205 = tlv("05", tx.substring(0, Math.min(35, tx.length())));
        String id62 = tlv("62", id6205);

        String partial = id00 + id01 + id26 + id52 + id53 + id54 + id58 + id59 + id60 + id62 + "6304";
        String crc = crc16(partial);
        return partial + crc;
    }

    /** CRC16-CCITT (0x1021), inicial 0xFFFF */
    public static String crc16(String data) {
        int crc = 0xFFFF;
        for (char c : data.toCharArray()) {
            crc ^= (c & 0xFF) << 8;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) crc = (crc << 1) ^ 0x1021;
                else crc = (crc << 1);
                crc &= 0xFFFF;
            }
        }
        return String.format("%04X", crc);
    }
}
