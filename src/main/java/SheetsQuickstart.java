import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import ru.cwl.googlesimport.GTService;
import ru.cwl.mappers.FactMapper;
import ru.cwl.mappers.RowMapper;
import ru.cwl.mappers.TemplPlanMapper;
import ru.cwl.model.Fact;
import ru.cwl.model.TemplPlan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SheetsQuickstart {



    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = GTService.getSheetsService();

        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
        String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        String range = "Class Data!A2:E";
        //print(service, spreadsheetId, range);
        spreadsheetId = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        range = "'ф1'!A2:H";
        List<Fact> r = load(service, spreadsheetId, range, new FactMapper());
        print(r);

       /* range="'факты.мес'!A2:H";
        print(service, spreadsheetId, range);*/

        range = "'планы с Q4Y2016'!A3:L";
        List<TemplPlan> r2 = load(service, spreadsheetId, range, new TemplPlanMapper());
        print(r2);

    }


    private static void print(List<? extends Object> list) {
        String s = list.stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
        System.out.println(s);
    }

/*    private static List<ru.cwl.model.Fact> load(Sheets service, String spreadsheetId, String range, ru.cwl.mappers.FactMapper m) throws IOException {
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        List<ru.cwl.model.Fact> result = new ArrayList<>();

        if (values != null && values.size() > 0) {
            for (List row : values) {
                ru.cwl.model.Fact v = m.map(row);
                if(v.getDate()!=null&&v.getAmount()!=null) {
                    result.add(v);
                }
            }
        }
        return result;
    }

    private static List<ru.cwl.model.TemplPlan> load(Sheets service, String spreadsheetId, String range, ru.cwl.mappers.TemplPlanMapper m) throws IOException {
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        List<ru.cwl.model.TemplPlan> result = new ArrayList<>();

        if (values != null && values.size() > 0) {
            for (List row : values) {
                ru.cwl.model.TemplPlan v = m.map(row);
                //if(v.getDate()!=null&&v.getAmount()!=null) {
                result.add(v);
                //}
            }
        }
        return result;
    }*/

    private static <T> List<T> load(Sheets service, String spreadsheetId, String range, RowMapper<T> m) throws IOException {
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        List<T> result = new ArrayList<T>();

        if (values != null && values.size() > 0) {
            for (List row : values) {
                T v = m.map(row);
                //if(v.getDate()!=null&&v.getAmount()!=null) {
                result.add(v);
                //}
            }
        }
        return result;
    }

}

