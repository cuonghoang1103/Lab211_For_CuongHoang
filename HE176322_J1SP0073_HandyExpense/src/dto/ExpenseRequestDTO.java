package dto;

import java.util.ArrayList;
import java.util.Date;

/**
 * DTO carrying what main read - the keyboard, or the data file at start-up - FROM main
 * INTO the controller.
 *
 * @author HE176322
 */
public class ExpenseRequestDTO {

    // ID of the expense to delete (option 3 only).
    private int id;

    // Date typed by the user, already parsed (option 1).
    private Date date;

    // Amount typed by the user, already checked > 0 (option 1).
    private double amount;

    // Content typed by the user, not empty (option 1).
    private String content;

    // Lines of the data file, read by main at start-up.
    private ArrayList<String> lineList;

    // Creates an empty request; main fills it through the setters.
    public ExpenseRequestDTO() {
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Sets the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the date.
    public Date getDate() {
        return date;
    }

    // Sets the date.
    public void setDate(Date date) {
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

    // Returns the lines of the data file.
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines of the data file.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }
}
