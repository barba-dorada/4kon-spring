package ru.cwl;

import ru.cwl.model.DateMethod;
import ru.cwl.model.Plan;
import ru.cwl.model.TemplPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 28.10.2016.
 */
public class PlanGenerator {

    public List<Plan> generate(List<TemplPlan> t, LocalDate from, LocalDate to) {
        List<Plan> result = new ArrayList<>();
        for (TemplPlan templPlan : t) {
            result.addAll(generate(templPlan, from, to));
        }
        return result;
    }

    // TODO: 30.10.2016 написать для месяца 
    // TODO: 30.10.2016 написать для квартала
    // TODO: 31.10.2016 написать для недели
    // TODO: 30.10.2016 перенести функциональность в енум
    // TODO: 31.10.2016 написать для года
    // на выходе список конкретных планов
    public List<Plan> generate(TemplPlan template, LocalDate from, LocalDate to) {
        List<Plan> result = new ArrayList<Plan>();
        DateMethod m = template.getDateMethod();
        LocalDate firstDate = m.getFirstDate(template.getFirstDate(), from);
            // TODO: 30.10.2016 вычислять минимум из
            /*
            1. есть только to->to
            2. есть to и lastDate->min(to,lastdate)
            3. есть to fromDate и колво повторов->min(to,fromdate+adder(колво))
            4. а если нет FromDate?
                         */
        LocalDate lastDate = to;// min(to,            t.getLastDate());

        // генерируем последовательность...
        for (; firstDate.isBefore(lastDate); firstDate = m.next(firstDate)) {
            Plan plan = new Plan();
            plan.setDate(firstDate);
            plan.setCategory(template.getCateory());
            plan.setAmount(template.getAmount());
            plan.setComment(template.getComment());
            result.add(plan);
        }
        return result;
    }
}