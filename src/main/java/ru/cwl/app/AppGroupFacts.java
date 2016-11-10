package ru.cwl.app;

import com.google.api.services.sheets.v4.Sheets;
import ru.cwl.googlesimport.GTService;
import ru.cwl.mappers.FactMapper;
import ru.cwl.model.Fact;
import ru.cwl.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AppGroupFacts {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = GTService.getSheetsService();

        final String SHEET_ID = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        final String FACTS = "'ф1'!A2:H";
        List<Fact> facts = Util.load(service, SHEET_ID, FACTS, new FactMapper());
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

}

