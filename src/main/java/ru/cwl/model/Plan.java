package ru.cwl.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by tischenko on 01.11.2016.
 */
public class Plan {
    LocalDate date;
    BigDecimal amount;
    String category;
    String comment;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "date=" + date +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
