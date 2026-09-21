package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean. The view prints only the fields the option filled; the others stay null.
 *
 * @author HE176322
 */
public class StudentResponseDTO {

    // One-line result: "Student [S01] has been updated.", "No student found."...
    private String message;

    // Create: one "Student [id] has been added." per stored student.
    private ArrayList<String> messageList;

    // Find and Sort: one row per matching student (Student.toString()), sorted by name.
    private ArrayList<String> searchRowList;

    // Report: one "name | course | total" line per group (ReportItem.toString()).
    private ArrayList<String> reportRowList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public StudentResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Sets the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the lines of Create.
    public ArrayList<String> getMessageList() {
        return messageList;
    }

    // Sets the lines of Create.
    public void setMessageList(ArrayList<String> messageList) {
        this.messageList = messageList;
    }

    // Returns the rows of Find and Sort.
    public ArrayList<String> getSearchRowList() {
        return searchRowList;
    }

    // Sets the rows of Find and Sort.
    public void setSearchRowList(ArrayList<String> searchRowList) {
        this.searchRowList = searchRowList;
    }

    // Returns the lines of the report.
    public ArrayList<String> getReportRowList() {
        return reportRowList;
    }

    // Sets the lines of the report.
    public void setReportRowList(ArrayList<String> reportRowList) {
        this.reportRowList = reportRowList;
    }
}
