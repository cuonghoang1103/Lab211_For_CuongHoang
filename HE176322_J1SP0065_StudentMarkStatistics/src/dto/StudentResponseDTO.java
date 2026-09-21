package dto;

/**
 * DTO carrying one classified student FROM the controller OUT TO the view: exactly the
 * four lines of a "Student Info" block.
 *
 * @author HE176322
 */
public class StudentResponseDTO {

    // Student name (line "Name:").
    private String name;

    // Class name (line "Classes:").
    private String classes;

    // Average, one decimal (line "AVG:").
    private double average;

    // Type A/B/C/D (line "Type:").
    private String type;

    // JavaBean constructor: an empty block, filled through the setters.
    public StudentResponseDTO() {
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the class name.
    public String getClasses() {
        return classes;
    }

    // Sets the class name.
    public void setClasses(String classes) {
        this.classes = classes;
    }

    // Returns the average.
    public double getAverage() {
        return average;
    }

    // Sets the average.
    public void setAverage(double average) {
        this.average = average;
    }

    // Returns the type.
    public String getType() {
        return type;
    }

    // Sets the type.
    public void setType(String type) {
        this.type = type;
    }
}
