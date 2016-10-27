import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by tischenko on 27.10.2016.
 */
public class TemplPlan {

    String comment;
    BigDecimal amount;
    String cateory;
    LocalDate firstDate;
    LocalDate lastDate;
    int perYear;
    BigDecimal totalPerYear;



    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCateory() {
        return cateory;
    }

    public void setCateory(String cateory) {
        this.cateory = cateory;
    }

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public int getPerYear() {
        return perYear;
    }

    public void setPerYear(int perYear) {
        this.perYear = perYear;
    }

    public BigDecimal getTotalPerYear() {
        return totalPerYear;
    }

    public void setTotalPerYear(BigDecimal totalPerYear) {
        this.totalPerYear = totalPerYear;
    }

    @Override
    public String toString() {
        return "TemplPlan{" +
                " cateory='" + cateory + '\'' +
                ", comment='" + comment + '\'' +
                ", amount=" + amount +
                ", firstDate=" + firstDate +
                ", lastDate=" + lastDate +
                ", perYear=" + perYear +
                ", totalPerYear=" + totalPerYear +
                '}';
    }
}
