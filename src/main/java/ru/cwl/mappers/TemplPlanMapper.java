package ru.cwl.mappers;

import ru.cwl.model.DateMethod;
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
        //v.setPerYear(Integer.parseInt(row.get(6).toString()));
        v.setFirstDate(toLocalDate(row.get(5).toString()));
        v.setDateMethod(toMethod(row.get(3).toString()));
        v.setTotalPerYear(toBigDecimal(row.get(7).toString()));
        return v;
    }

    @Override
    public boolean isValid(TemplPlan v) {
        return v.getAmount() != null && v.getCateory() != null;
    }

    DateMethod toMethod(String name) {
        for (DateMethod dateMethod : DateMethod.values()) {
            if (dateMethod.getRname().equalsIgnoreCase(name)) {
                return dateMethod;
            }
        }
        return DateMethod.MONTHLY;
    }
}

