package ru.cwl.model;

/**
 * Created by admin on 29.10.2016.
 */
public enum DateMethod {
    YEARLY("ежегодно"),
    MONTHLY("ежемесячно"),
    WEEKLY("еженедельно"), DAYLY("ежедневно"),
    QUWARTERLY("ежеквартально"),
    ONCE("разово");
    String rname;

    public String getRname() {
        return rname;
    }

    DateMethod(String s) {
        rname = s;
    }
}
