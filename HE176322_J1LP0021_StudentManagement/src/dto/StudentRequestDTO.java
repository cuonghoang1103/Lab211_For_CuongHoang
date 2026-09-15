package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller - a JavaBean.
 *
 * @author HE176322
 */
public class StudentRequestDTO {

    // Id typed by the user (create, find by id).
    private String id;
    // Name typed by the user; empty on update means "keep".
    private String studentName;
    // Semester typed by the user.
    private Integer semester;
    // Course typed by the user, still as text; empty on update = "keep".
    private String courseName;
    // Name or part of a name to search for (Find and Sort only).
    private String searchText;

    // Creates an empty request; main fills it through the setters.
    public StudentRequestDTO() {
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
}
