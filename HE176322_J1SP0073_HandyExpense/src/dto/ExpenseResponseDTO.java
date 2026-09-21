package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean. Add and delete fill the message; display fills the rows and the total (or the
 * message when the book is empty).
 *
 * @author HE176322
 */
public class ExpenseResponseDTO {

    // The one-line result, e.g. "Add an expense successful"; null when the answer is a
    // table.
    private String message;

    // The table rows, one per expense (the text of Expense.toString()); null when the
    // answer is a message.
    private ArrayList<String> rowList;

    // The total of all amounts, printed under the rows.
    private double total;

    // JavaBean constructor: an empty answer, filled through the setters.
    public ExpenseResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Sets the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the table rows.
    public ArrayList<String> getRowList() {
        return rowList;
    }

    // Sets the table rows.
    public void setRowList(ArrayList<String> rowList) {
        this.rowList = rowList;
    }

    // Returns the total.
    public double getTotal() {
        return total;
    }

    // Sets the total.
    public void setTotal(double total) {
        this.total = total;
    }
}
