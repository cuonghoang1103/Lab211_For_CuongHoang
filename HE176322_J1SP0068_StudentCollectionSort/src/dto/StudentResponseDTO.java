package dto;

/**
 * DTO carrying one sorted student FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class StudentResponseDTO {

    // Name shown on the "Name:" line.
    private String name;
    // Class shown on the "Classes:" line.
    private String classes;
    // Mark shown on the "Mark:" line.
    private float mark;

    // JavaBean constructor: an empty row, filled through the setters.
    public StudentResponseDTO() {
    }

    // Creates the response with every field filled in.
    public StudentResponseDTO(String name, String classes, float mark) {
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
