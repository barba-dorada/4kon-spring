package ru.cwl.model;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.*;

/**
 * Created by admin on 29.10.2016.
 */

public enum DateMethod {
    YEARLY("ежегодно"){
        @Override
        public LocalDate next(LocalDate cur) {
            return cur.plus(1,YEARS);
        }
    },
    MONTHLY("ежемесячно"){
        @Override
        public LocalDate next(LocalDate cur) {
            return cur.plus(1,MONTHS);
        }
    },
    WEEKLY("еженедельно"){
        @Override
        public LocalDate next(LocalDate cur) {
            return cur.plus(1,WEEKS);
        }
    },
    DAILY("ежедневно"){
        @Override
        public LocalDate next(LocalDate cur) {
            return cur.plus(1,DAYS);
        }
    },
    QUARTERLY("ежеквартально"){
        @Override
        public LocalDate next(LocalDate cur) {
            return cur.plus(3,MONTHS);
        }
    },
    ONCE("разово") {
        @Override
        public LocalDate next(LocalDate cur) {
            return LocalDate.MAX;
        }
    };
    // TODO: add workdays....

    String rname;

    public String getRname() {
        return rname;
    }
    public abstract LocalDate next(LocalDate cur);

    DateMethod(String s) {
        rname = s;
    }

    public LocalDate getFirstDate(LocalDate firstDate, LocalDate from) {
        if(firstDate==null){
            //Todo  это для месяца!!!! переделать под разные методы.
            firstDate=LocalDate.of(from.getYear(),from.getMonth(),1);
        }
        while(firstDate.isBefore(from)){
            firstDate=next(firstDate);
        }
        return firstDate;
    }
}
