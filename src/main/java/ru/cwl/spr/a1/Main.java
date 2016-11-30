package ru.cwl.spr.a1;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.cwl.dao.FactDaoInt;
import ru.cwl.dao.GTFactDao;
import ru.cwl.dao.JdbcTemplateFactDao;
import ru.cwl.model.Fact;
import ru.cwl.util.AggrTable;
import ru.cwl.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/spring/context.xml");
        ctx.refresh();

        FactDaoInt dbDao = ctx.getBean("factDao", JdbcTemplateFactDao.class);
        FactDaoInt gtDao = ctx.getBean("gtFactDao", GTFactDao.class);

        Util.print(dbDao.findAll());

        List<Fact> allFromGT = gtDao.findAll();
        Util.print(allFromGT);
        allFromGT.forEach(dbDao::insert);
        List<Fact> allFromDB = dbDao.findAll();
        Fact f = allFromDB.get(0);
        f.setUser("A");
        f.setComment("comment 345");
        dbDao.update(f);

        dbDao.delete(2L);
        dbDao.delete(3L);

        System.out.println(dbDao.findById(5L));
        System.out.println(dbDao.findById(6L));

        allFromDB = dbDao.findAll();
        Util.print(allFromDB);

        AggrTable.printAgrTable(allFromGT);
        System.out.println();
        AggrTable.printAgrTable(allFromDB);

        List<Fact> res = dbDao.findFromTo(LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 5));
        Util.print(res);
    }
}