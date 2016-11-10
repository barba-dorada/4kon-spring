package ru.cwl.spr.a1;

import com.google.api.services.sheets.v4.Sheets;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.cwl.util.AggrTable;
import ru.cwl.util.Util;
import ru.cwl.mappers.FactMapper;
import ru.cwl.model.Fact;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/spring/context.xml");
        ctx.refresh();
        Sheets gtService = ctx.getBean("gtService", Sheets.class);

        final String SHEET_ID = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        final String FACTS = "'ф1'!A2:H";
        List<Fact> facts = Util.load(gtService, SHEET_ID, FACTS, new FactMapper());
        Util.print(facts);
        AggrTable.printAgrTable(facts);
    }
}
