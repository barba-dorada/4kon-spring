package ru.cwl.spr.a1;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.cwl.dao.FactDaoInt;
import ru.cwl.dao.GTFactDao;
import ru.cwl.dao.JdbcTemplateFactDao;
import ru.cwl.model.Fact;
import ru.cwl.util.AggrTable;
import ru.cwl.util.Util;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/spring/context.xml");
        ctx.refresh();
        //Sheets gtService = ctx.getBean("gtService", Sheets.class);

        DataSource ds = ctx.getBean("dataSource", DataSource.class);
        JdbcTemplate t = new JdbcTemplate();
        t.setDataSource(ds);

        JdbcTemplateFactDao dao = ctx.getBean("factDao", JdbcTemplateFactDao.class);//new JdbcTemplateFactDao();
        //dao.setDataSource(ds);
        Util.print(dao.findAll());

        FactDaoInt dd = ctx.getBean("gtFactDao", GTFactDao.class);
        List<Fact> facts = dd.findAll();
        Util.print(facts);
        for (Fact fact : facts) {
            dao.insert(fact);
        }
        List<Fact> all = dao.findAll();
        Util.print(all);

        AggrTable.printAgrTable(facts);
        System.out.println();
        AggrTable.printAgrTable(all);
    }
}