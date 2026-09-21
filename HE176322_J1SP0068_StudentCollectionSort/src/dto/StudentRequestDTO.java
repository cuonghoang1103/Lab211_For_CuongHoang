package dto;

import java.util.ArrayList;

/**
 * DTO carrying what the user typed FROM main INTO the controller: every student entered
 * in Function 1, in the order they were typed.
 *
 * @author HE176322
 */
public class StudentRequestDTO {

    // The students typed by the user, first typed first.
    private ArrayList<StudentDTO> studentList;

    // Creates an empty request; main fills it through the setter.
    public StudentRequestDTO() {
        studentList = new ArrayList<>();
    }

    // Returns the students typed.
    public ArrayList<StudentDTO> getStudentList() {
        return studentList;
    }

    // Sets the students typed.
    public void setStudentList(ArrayList<StudentDTO> studentList) {
        this.studentList = studentList;
    }
}
