package dto;

import constants.Constants;

/**
 * DTO carrying one report line FROM the controller OUT TO the view - a JavaBean.
 *
 * @author HE176322
 */
public class ReportResponseDTO {

    // Student name of the line.
    private String studentName;
    // Course label of the line.
    private String courseName;
    // How many times the student took the course.
    private int total;

    // JavaBean constructor: an empty line, filled through the setters.
    public ReportResponseDTO() {
    }

    // Creates the line with every column filled in.
    public ReportResponseDTO(String studentName, String courseName, int total) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.total = total;
    }

    // Returns the name.
    public String getStudentName() {
        return studentName;
    }

    // Sets the name.
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Returns the course label.
    public String getCourseName() {
        return courseName;
    }

    // Sets the course label.
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Returns the total.
    public int getTotal() {
        return total;
    }

    // Sets the total.
    public void setTotal(int total) {
        this.total = total;
    }

    // The brief's report line: "Nguyen Van A | Java | 2".
    @Override
    public String toString() {
        return String.format(Constants.REPORT_FORMAT, studentName, courseName, total);
    }
}
