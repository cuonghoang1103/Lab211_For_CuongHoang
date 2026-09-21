package dto;

/**
 * DTO carrying one student the user typed; main puts one per student into the
 * ReportRequestDTO that goes INTO the controller.
 *
 * @author HE176322
 */
public class StudentRequestDTO {

    // Name typed by the user.
    private String name;

    // Class typed by the user.
    private String classes;

    // Maths mark typed by the user (already checked 0..10).
    private double maths;

    // Chemistry mark typed by the user (already checked 0..10).
    private double chemistry;

    // Physics mark typed by the user (already checked 0..10).
    private double physics;

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

    // Returns the maths mark.
    public double getMaths() {
        return maths;
    }

    // Sets the maths mark.
    public void setMaths(double maths) {
        this.maths = maths;
    }

    // Returns the chemistry mark.
    public double getChemistry() {
        return chemistry;
    }

    // Sets the chemistry mark.
    public void setChemistry(double chemistry) {
        this.chemistry = chemistry;
    }

    // Returns the physics mark.
    public double getPhysics() {
        return physics;
    }

    // Sets the physics mark.
    public void setPhysics(double physics) {
        this.physics = physics;
    }
}
