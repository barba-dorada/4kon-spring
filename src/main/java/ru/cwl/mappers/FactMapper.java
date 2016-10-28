package ru.cwl.mappers;

import ru.cwl.model.Fact;

import java.util.List;

/**
 * Created by tischenko on 28.10.2016.
 */
public class FactMapper implements RowMapper<Fact>, MUtils {
    // TODO: 26.10.2016 add filter
    public Fact map(List row) {
        Fact res = new Fact();
        res.setUser(row.get(0).toString());
        res.setDate(toLocalDate(row.get(1).toString()));
        res.setAccount(row.get(2).toString());
        res.setCateory(row.get(3).toString());
        String money = row.get(4).toString();
        res.setAmount(toBigDecimal(money));
        res.setComment(row.get(5).toString());
        res.setSubtotal(toBigDecimal(row.get(6).toString()));
        res.setMonth(row.get(7).toString());
        return res;
    }

    @Override
    public boolean isValid(Fact v) {
        return v.getDate()!=null;
    }

}
