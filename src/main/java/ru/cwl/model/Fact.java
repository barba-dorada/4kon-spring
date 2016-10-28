package ru.cwl.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by admin on tring26.10.2016.
 */
public class Fact {
    String user;
    LocalDate date;
    String account;
    String cateory;
    BigDecimal amount;
    String comment;
    BigDecimal subtotal;
    String month;

    @Override
    public String toString() {
        return "ru.cwl.model.Fact{" +
                "user='" + user + '\'' +
                ", date=" + date +
                ", account='" + account + '\'' +
                ", cateory='" + cateory + '\'' +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", subtotal=" + subtotal +
                ", month='" + month + '\'' +
                '}';
    }

    public String getCateory() {
        return cateory;
    }

    public void setCateory(String cateory) {
        this.cateory = cateory;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

}
