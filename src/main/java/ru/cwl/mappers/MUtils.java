package ru.cwl.mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by tischenko on 28.10.2016.
 */
interface MUtils {
    default BigDecimal toBigDecimal(String from) {
        String money = from;
        money = money.replaceAll(",", ".");
        money = money.replace(" ", "");

        if (money.length() > 0) {
            BigDecimal bd = new BigDecimal(money);
            return bd;
        }
        return null;
    }

    default LocalDate toLocalDate(String from) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            final LocalDate dt = LocalDate.parse(from, dtf);
            return dt;
        } catch (Exception e) {
        }
        return null;
    }
}
