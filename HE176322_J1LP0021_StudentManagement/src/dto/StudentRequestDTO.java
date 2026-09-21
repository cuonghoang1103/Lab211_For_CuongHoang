package dto;

import java.util.ArrayList;

/**
 * DTO carrying what the user typed FROM main INTO the controller - a JavaBean. Create uses
 * the four fields for the student being typed and studentList for the students already
 * checked; Find and Sort uses searchText; Update/Delete uses id, option and the new values.
 *
 * @author HE176322
 */
public class StudentRequestDTO {

    // Id typed by the user (create, find by id).
    private String id;

    // Name typed by the user; empty on update means "keep".
    private String studentName;

    // Semester typed by the user; null on update means "keep".
    private Integer semester;

    // Course typed by the user, still as text; empty on update means "keep".
    private String courseName;

    // Name or part of a name to search for (Find and Sort only).
    private String searchText;

    // The answer to "update (U) or delete (D)": Constants.UPDATE or Constants.DELETE.
    private String option;

    // Create: the students typed and checked so far, in the typed order.
    private ArrayList<StudentRequestDTO> studentList;

    // Creates an empty request; main fills it through the setters.
    public StudentRequestDTO() {
        studentList = new ArrayList<>();
    }

    // Creates the request of one student with its four fields (a student Create keeps).
    public StudentRequestDTO(String id, String studentName, Integer semester,
            String courseName) {
        this.id = id;
        this.studentName = studentName;
        this.semester = semester;
        this.courseName = courseName;
        this.studentList = new ArrayList<>();
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
    public Integer getSemester() {
        return semester;
    }

    // Sets the semester.
    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    // Returns the course text.
    public String getCourseName() {
        return courseName;
    }

    // Sets the course text.
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Returns the search text.
    public String getSearchText() {
        return searchText;
    }

    // Sets the search text.
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    // Returns the answer U or D.
    public String getOption() {
        return option;
    }

    // Sets the answer U or D.
    public void setOption(String option) {
        this.option = option;
    }

    // Returns the students kept so far in this Create.
    public ArrayList<StudentRequestDTO> getStudentList() {
        return studentList;
    }

    // Sets the students kept so far in this Create.
    public void setStudentList(ArrayList<StudentRequestDTO> studentList) {
        this.studentList = studentList;
    }
}
