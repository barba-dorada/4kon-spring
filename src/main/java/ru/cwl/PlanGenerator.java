package ru.cwl;

import com.sun.istack.internal.NotNull;
import ru.cwl.model.DateMethod;
import ru.cwl.model.TemplPlan;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.MONTHS;

/**
 * Created by admin on 28.10.2016.
 */
public class PlanGenerator {
    public void generate(List<TemplPlan> t, LocalDate from, LocalDate to) {
        for (TemplPlan templPlan : t) {
            generate(templPlan, from, to);

        }
    }

    // TODO: 30.10.2016 написать для месяца 
    // TODO: 30.10.2016 написать для квартала
    // TODO: 31.10.2016 написать для недели
    // TODO: 30.10.2016 перенести функциональность в енум
    // TODO: 31.10.2016 написать для года
    // на выходе список конкретных планов
    public void generate(TemplPlan template, @NotNull LocalDate from, @NotNull LocalDate to) {
        if (template.getDateMethod() == DateMethod.MONTHLY) {
            // вычисляем первую дату...
            LocalDate firstDate=template.getFirstDate();
            if(firstDate==null){
                // firstdate den
                firstDate=LocalDate.of(from.getYear(),from.getMonth(),1);
            }
            while(firstDate.isBefore(from)){
                // date adder
                firstDate=firstDate.plus(1,MONTHS);
            }

            // TODO: 30.10.2016 вычислять минимум из
            /*
            1. есть только to->to
            2. есть to и lastDate->min(to,lastdate)
            3. есть to fromDate и колво повторов->min(to,fromdate+adder(колво))
            4. а если нет FromDate?
                         */
            LocalDate lastDate =to;// min(to,            t.getLastDate());
            // генерируем последовательность...
            for(LocalDate i=firstDate; firstDate.isBefore(lastDate); firstDate=firstDate.plus(1,MONTHS)){
                System.out.printf("%s %s %s\n",firstDate,template.getCateory(),template.getAmount());
            }
        }
    }
}