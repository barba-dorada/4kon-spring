package ru.cwl.app;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import ru.cwl.mappers.RowMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 09.11.2016.
 */
public class Util {
    public static <T> List<T> load(Sheets service, String spreadsheetId, String range, RowMapper<T> m) throws IOException {
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

    private static void print(List<? extends Object> list) {
        String name = list.get(0).getClass().getName();
        System.out.println(name + "____________________________");
        String s = list.stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
        System.out.println(s);
    }
}
