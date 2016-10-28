package ru.cwl.mappers;

import ru.cwl.model.TemplPlan;

import java.util.List;

/**
 * Created by tischenko on 28.10.2016.
 */
public class TemplPlanMapper implements RowMapper<TemplPlan>, MUtils {
    public TemplPlan map(List row) {
        TemplPlan v = new TemplPlan();
        if (row.isEmpty()) {
            return v;
        }
        v.setCateory(row.get(0).toString());
        v.setComment(row.get(1).toString());
        v.setAmount(toBigDecimal(row.get(2).toString()));
        v.setFirstDate(toLocalDate(row.get(5).toString()));
        v.setTotalPerYear(toBigDecimal(row.get(7).toString()));
        return v;
    }
}
