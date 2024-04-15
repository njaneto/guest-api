package com.church.guest.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.SneakyThrows;

import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CsvUtil {

    @SneakyThrows
    public static <T> void writer(List<T> fetchAll, Writer writer) {

        StatefulBeanToCsv<T> csv = new StatefulBeanToCsvBuilder<T>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator('|')
                .withOrderedResults(true)
                .build();

        csv.write(fetchAll);
    }

    public static <T> String buildHeader(Class<T> tClass) {

        return Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.getAnnotation(CsvBindByPosition.class) != null
                        && field.getAnnotation(CsvBindByName.class) != null )
                .sorted(Comparator.comparing(field -> field.getAnnotation(CsvBindByPosition.class).position()))
                .map(field -> field.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(Character.toString('|')))
                .toUpperCase(Locale.ROOT)
                .concat(CSVWriter.DEFAULT_LINE_END);
    }
}
