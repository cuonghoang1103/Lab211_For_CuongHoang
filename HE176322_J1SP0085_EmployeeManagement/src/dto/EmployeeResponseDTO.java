package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean. Add, update and remove fill the message (update also the employee on one line);
 * search fills the rows of its table (or the message when nobody matches); sort fills the
 * rows of the sorted list.
 *
 * @author HE176322
 */
public class EmployeeResponseDTO {

    // The one-line result, e.g. "=> Employee E001 added successfully."; null for a table.
    private String message;

    // The employee just updated, on one line (the text of Employee.toString()).
    private String detail;

    // The rows of the search table, one per matching employee; null for other answers.
    private ArrayList<String> searchRowList;

    // The rows of the list sorted by salary, one per employee; null for other answers.
    private ArrayList<String> sortRowList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public EmployeeResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Sets the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the employee just updated, on one line.
    public String getDetail() {
        return detail;
    }

    // Sets the employee just updated, on one line.
    public void setDetail(String detail) {
        this.detail = detail;
    }

    // Returns the rows of the search table.
    public ArrayList<String> getSearchRowList() {
        return searchRowList;
    }

    // Sets the rows of the search table.
    public void setSearchRowList(ArrayList<String> searchRowList) {
        this.searchRowList = searchRowList;
    }

    // Returns the rows of the sorted list.
    public ArrayList<String> getSortRowList() {
        return sortRowList;
    }

    // Sets the rows of the sorted list.
    public void setSortRowList(ArrayList<String> sortRowList) {
        this.sortRowList = sortRowList;
    }
}
