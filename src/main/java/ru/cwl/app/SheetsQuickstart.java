package ru.cwl.app;

import com.google.api.services.sheets.v4.Sheets;
import ru.cwl.PlanGenerator;
import ru.cwl.googlesimport.GTService;
import ru.cwl.mappers.TemplPlanMapper;
import ru.cwl.model.Plan;
import ru.cwl.model.TemplPlan;
import ru.cwl.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SheetsQuickstart {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = GTService.getSheetsService();

        final String SHEET_ID = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
       /* final String FACTS = "'ф1'!A2:H";
        List<Fact> facts = load(service, SHEET, FACTS, new FactMapper());
        print(facts);*/

        String PLANS = "'планы с Q4Y2016'!A3:L";
        List<TemplPlan> templPlans = Util.load(service, SHEET_ID, PLANS, new TemplPlanMapper());

        List<TemplPlan> sl = templPlans.subList(0, 5);
        print(sl);
        PlanGenerator pg = new PlanGenerator();
        List<Plan> res = pg.generate(sl, LocalDate.of(2016, 10, 1), LocalDate.of(2017, 8, 1));
        print(res);
       /* range="'факты.мес'!A2:H";
        print(service, spreadsheetId, range);*/
    }

    private static void print(List<? extends Object> list) {
        String name = list.get(0).getClass().getName();
        System.out.println(name+"____________________________");
        String s = list.stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
        System.out.println(s);
    }




}

