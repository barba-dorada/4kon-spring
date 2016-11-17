package ru.cwl.util;

import ru.cwl.model.Fact;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by tischenko on 10.11.2016.
 */
public class AggrTable {
    public static void printAgrTable(List<Fact> facts) {
        T2 t = new T2();
        for (Fact fact : facts) {
            t.add(fact.getCateory(),fact.getDate().toString().substring(0,7),fact.getAmount());
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
