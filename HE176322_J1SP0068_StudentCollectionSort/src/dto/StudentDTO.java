package dto;

/**
 * DTO carrying ONE student (name, class, mark) between the layers: inside the request
 * (typed by the user, main -> controller) and inside the response (sorted, controller ->
 * view). A JavaBean; the view never sees the model Student itself.
 *
 * @author HE176322
 */
public class StudentDTO {

    // Name of the student.
    private String name;

    // Class of the student.
    private String classes;

    // Mark of the student (already validated by main).
    private float mark;

    // JavaBean constructor: an empty row, filled through the setters.
    public StudentDTO() {
    }

    // Creates the row with every field filled in.
    public StudentDTO(String name, String classes, float mark) {
        this.name = name;
        this.classes = classes;
        this.mark = mark;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the class.
    public String getClasses() {
        return classes;
    }

    // Sets the class.
    public void setClasses(String classes) {
        this.classes = classes;
    }

    // Returns the mark.
    public float getMark() {
        return mark;
    }

    // Sets the mark.
    public void setMark(float mark) {
        this.mark = mark;
    }
}
