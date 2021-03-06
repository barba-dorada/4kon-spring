package ru.cwl.app;

import com.google.api.services.sheets.v4.Sheets;
import ru.cwl.googlesimport.GTService;
import ru.cwl.mappers.FactMapper;
import ru.cwl.model.Fact;
import ru.cwl.util.T2;
import ru.cwl.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AppGroupFacts2 {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = GTService.getSheetsService();

        final String SHEET_ID = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        final String FACTS = "'ф1'!A2:H";
        List<Fact> facts = Util.load(service, SHEET_ID, FACTS, new FactMapper());
//        print(facts);
        T2 t = new T2();
        for (Fact fact : facts) {
            t.add(fact.getCateory(),fact.getMonth(),fact.getAmount());
        }

        // TODO: 08.11.2016 add columns total
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
}

