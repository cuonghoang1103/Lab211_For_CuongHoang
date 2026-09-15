package dto;

/**
 * DTO carrying one student typed by the user FROM main INTO the controller.
 *
 * @author HE176322
 */
public class StudentRequestDTO {

    // Name typed by the user.
    private String name;
    // Class typed by the user.
    private String classes;
    // Mark typed by the user (already validated).
    private float mark;

    // Creates an empty request; main fills it through the setters.
    public StudentRequestDTO() {
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
