package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the students sorted by
 * name from A to Z, as rows the view is allowed to see.
 *
 * @author HE176322
 */
public class StudentResponseDTO {

    // The sorted students, Student 1 first.
    private ArrayList<StudentDTO> studentList;

    // JavaBean constructor: an empty response, filled through the setter.
    public StudentResponseDTO() {
        studentList = new ArrayList<>();
    }

    // Returns the sorted students.
    public ArrayList<StudentDTO> getStudentList() {
        return studentList;
    }

    // Sets the sorted students.
    public void setStudentList(ArrayList<StudentDTO> studentList) {
        this.studentList = studentList;
    }
}
