package ru.cwl.app;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import ru.cwl.PlanGenerator;
import ru.cwl.googlesimport.GTService;
import ru.cwl.mappers.FactMapper;
import ru.cwl.mappers.RowMapper;
import ru.cwl.mappers.TemplPlanMapper;
import ru.cwl.model.Fact;
import ru.cwl.model.Plan;
import ru.cwl.model.TemplPlan;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AppGroupFacts {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = GTService.getSheetsService();

        final String SHEET_ID = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        final String FACTS = "'ф1'!A2:H";
        List<Fact> facts = load(service, SHEET_ID, FACTS, new FactMapper());
//        print(facts);
        Map<String, BigDecimal> map = new HashMap<>();
        for (Fact fact : facts) {
            if (fact.getDate().getMonthValue() == 10) {
                BigDecimal oldVal = map.get(fact.getCateory());
                if (oldVal == null) {
                    oldVal = new BigDecimal("0.00");
                }
                map.put(fact.getCateory(), oldVal.add(fact.getAmount()));
            }
        }
        BigDecimal count = BigDecimal.ZERO;
        ArrayList<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            System.out.printf("%-15s: %15s\n", key, map.get(key));
            count = count.add(map.get(key));
        }
        System.out.println("---------------:----------------");
        System.out.printf("total          : %15s\n", count);
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

class Tab2<V, D1, D2> {
    private final V z;
    Map<D1, Map<D2, V>> m = new HashMap<>();

    Tab2(V zero) {
        z = zero;
    }

    V get(D1 d1, D2 d2) {
        Map<D2, V> v1 = m.get(d1);
        if (v1 != null) {
            V v2 = v1.get(d2);
            if (v2 != null) {
                return v2;
            }
        }
        return z;
    }

    void add(D1 d1, D2 d2, V v) {
        V v0 = get(d1, d2);
        v0 = add(v0, v);

        Map<D2, V> v1 = m.get(d1);
        if (v1 == null) {
            v1 = new HashMap<D2, V>();
            m.put(d1, v1);
        }
        v1.put(d2, v0);
    }

    V add(V v0, V v1) {
        return null;
    }
    List<D1> getRowsName(){
        return new ArrayList<D1>(m.keySet());
    }
    List<D2> getColumnsName(){
        Set<D2> s=new HashSet<D2>();
        for (D1 d1 : getRowsName()) {
            s.addAll(m.get(d1).keySet());
        }
        return new ArrayList<D2>(s);
    }

}

class T2 extends Tab2<BigDecimal,String,String>{

    T2() {
        super(new BigDecimal("0.00"));
    }

    @Override
    BigDecimal add(BigDecimal v0, BigDecimal v1) {
        return v0.add( v1);
    }
}