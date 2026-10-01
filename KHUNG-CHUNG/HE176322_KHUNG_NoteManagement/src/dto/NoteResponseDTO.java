package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one flow FROM the controller OUT TO the view: the line of an
 * add or a delete, or the rows of the table.
 *
 * @author HE176322
 */
public class NoteResponseDTO {

    // Options 1 and 2: the one line of the result.
    private String message;

    // Option 3: the rows of the table; null for options 1 and 2.
    private ArrayList<String> rowList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public NoteResponseDTO() {
    }

    // Returns the line of the result.
    public String getMessage() {
        return message;
    }

    // Sets the line of the result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the rows of the table.
    public ArrayList<String> getRowList() {
        return rowList;
    }

    // Sets the rows of the table.
    public void setRowList(ArrayList<String> rowList) {
        this.rowList = rowList;
    }
}
