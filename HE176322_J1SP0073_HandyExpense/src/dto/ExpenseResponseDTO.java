package dto;

import constants.Constants;
import utils.FormatUtils;

/**
 * DTO carrying one row of the expense table FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class ExpenseResponseDTO {

    // Column ID.
    private int id;
    // Column Date.
    private String date;
    // Column Amount.
    private double amount;
    // Column Content.
    private String content;

    // JavaBean constructor: an empty row, filled through the setters.
    public ExpenseResponseDTO() {
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Sets the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the date text.
    public String getDate() {
        return date;
    }

    // Sets the date text.
    public void setDate(String date) {
        this.date = date;
    }

    // Returns the amount.
    public double getAmount() {
        return amount;
    }

    // Sets the amount.
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Returns the content.
    public String getContent() {
        return content;
    }

    // Sets the content.
    public void setContent(String content) {
        this.content = content;
    }

    // One table row in fixed-width columns.
    @Override
    public String toString() {
        return String.format(Constants.ROW_FORMAT, id, date,
                FormatUtils.formatMoney(amount), content);
    }
}
