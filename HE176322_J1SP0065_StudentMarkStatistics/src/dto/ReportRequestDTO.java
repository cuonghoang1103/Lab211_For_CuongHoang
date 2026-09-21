package dto;

import java.util.ArrayList;

/**
 * DTO carrying what the user typed FROM main INTO the controller: every student entered
 * in Function 1, in the order they were typed.
 *
 * @author HE176322
 */
public class ReportRequestDTO {

    // The students typed by the user, first typed first.
    private ArrayList<StudentRequestDTO> studentList;

    // Creates an empty request; main fills it through the setter.
    public ReportRequestDTO() {
        studentList = new ArrayList<>();
    }

    // Returns the students typed.
    public ArrayList<StudentRequestDTO> getStudentList() {
        return studentList;
    }

    // Sets the students typed.
    public void setStudentList(ArrayList<StudentRequestDTO> studentList) {
        this.studentList = studentList;
    }
}
