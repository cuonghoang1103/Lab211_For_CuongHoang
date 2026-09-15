package dto;

import constants.Constants;

/**
 * DTO carrying one student FROM the controller OUT TO the view - a JavaBean.
 *
 * @author HE176322
 */
public class StudentResponseDTO {

    // Id shown in the first column.
    private String id;
    // Name shown on screen.
    private String studentName;
    // Semester shown on screen.
    private int semester;
    // Course label shown on screen.
    private String courseName;

    // JavaBean constructor: an empty row, filled through the setters.
    public StudentResponseDTO() {
    }

    // Creates the row with every column filled in.
    public StudentResponseDTO(String id, String studentName, int semester,
            String courseName) {
        this.id = id;
        this.studentName = studentName;
        this.semester = semester;
        this.courseName = courseName;
    }

    // Returns the id.
    public String getId() {
        return id;
    }

    // Sets the id.
    public void setId(String id) {
        this.id = id;
    }

    // Returns the name.
    public String getStudentName() {
        return studentName;
    }

    // Sets the name.
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Returns the semester.
    public int getSemester() {
        return semester;
    }

    // Sets the semester.
    public void setSemester(int semester) {
        this.semester = semester;
    }

    // Returns the course label.
    public String getCourseName() {
        return courseName;
    }

    // Sets the course label.
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // The full row shown by Update/Delete, already padded into columns.
    @Override
    public String toString() {
        return String.format(Constants.STUDENT_ROW_FORMAT, id, studentName,
                semester, courseName);
    }
}
