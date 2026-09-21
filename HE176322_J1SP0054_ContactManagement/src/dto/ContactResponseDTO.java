package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean. Add and delete fill the message; display fills the table rows.
 *
 * @author HE176322
 */
public class ContactResponseDTO {

    // The one-line result, e.g. "Successful"; null when the answer is a table.
    private String message;

    // The table rows, one per contact (the text of Contact.toString()); null when the
    // answer is a message.
    private ArrayList<String> rowList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public ContactResponseDTO() {
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
