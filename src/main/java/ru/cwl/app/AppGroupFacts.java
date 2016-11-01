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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppGroupFacts {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = GTService.getSheetsService();

        final String SHEET_ID = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        final String FACTS = "'ф1'!A2:H";
        List<Fact> facts = load(service, SHEET_ID, FACTS, new FactMapper());
        print(facts);
        Map<String,BigDecimal> map=new HashMap<>();
        for (Fact fact : facts) {
            if(fact.getDate().getMonthValue()==10){
                BigDecimal oldVal = map.get(fact.getCateory());
                if(oldVal==null){
                    oldVal=new BigDecimal("0.00");
                }
                map.put(fact.getCateory(),oldVal.add(fact.getAmount()));
            }
        }
        BigDecimal count=BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
            System.out.printf("%-15s: %15s\n",entry.getKey(),entry.getValue());
            count=count.add(entry.getValue());
        }
        System.out.println("---------------:----------------");
        System.out.printf("total          : %15s\n",count);

    }

    private static void print(List<? extends Object> list) {
        String name = list.get(0).getClass().getName();
        System.out.println(name+"____________________________");
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
                if(m.isValid(v)) {
                    result.add(v);
                }
            }
        }
        return result;
    }


}

