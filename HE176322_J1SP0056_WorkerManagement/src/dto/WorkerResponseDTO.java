package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean. Add, up and down fill the message; display fills the table rows (or the message
 * when there is nothing to show).
 *
 * @author HE176322
 */
public class WorkerResponseDTO {

    // The one-line result, e.g. "Salary has been adjusted."; null when the answer is a
    // table.
    private String message;

    // The table rows, one per salary adjustment (the text of SalaryHistory.toString()); null
    // when the answer is a message.
    private ArrayList<String> rowList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public WorkerResponseDTO() {
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
}
