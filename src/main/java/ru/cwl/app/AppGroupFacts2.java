package ru.cwl.app;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import ru.cwl.googlesimport.GTService;
import ru.cwl.mappers.FactMapper;
import ru.cwl.mappers.RowMapper;
import ru.cwl.model.Fact;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class AppGroupFacts2 {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = GTService.getSheetsService();

        final String SHEET_ID = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        final String FACTS = "'ф1'!A2:H";
        List<Fact> facts = load(service, SHEET_ID, FACTS, new FactMapper());
//        print(facts);
        T2 t = new T2();
        for (Fact fact : facts) {
            t.add(fact.getCateory(),fact.getMonth(),fact.getAmount());
        }

        // * add columns & rows sorting
        // TODO: 08.11.2016 add columns
        // * rows total;
        // TODO add total;
        List<String> columns = t.getColumnsName();
        Collections.sort(columns);
        System.out.printf("%-15s ", "период");
        for (String column : columns) {
            System.out.printf("|%10s",column);
        }
        System.out.printf("|%10s\n","total");
        final List<String> rows = t.getRowsName();
        Collections.sort(rows);
        for (String row : rows) {
            BigDecimal total=BigDecimal.ZERO;
            System.out.printf("%-15s ", row);
            for (String column : columns) {
                final BigDecimal value = t.get(row, column);
                System.out.printf("|%10s", value.toString());
                total=total.add(value);
            }
            System.out.printf("|%10s\n", total);
        }
    }

    private static void print(List<? extends Object> list) {
        String name = list.get(0).getClass().getName();
        System.out.println(name + "____________________________");
        String s = list.stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
        System.out.println(s);
    }

    private static <T> List<T> load(Sheets service, String spreadsheetId, String range, RowMapper<T> m) throws IOException {
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        List<T> result = new ArrayList<T>();

        if (values != null && values.size() > 0) {
            for (List row : values) {
                T v = m.map(row);
                if (m.isValid(v)) {
                    result.add(v);
                }
            }
        }
        return result;
    }


}

