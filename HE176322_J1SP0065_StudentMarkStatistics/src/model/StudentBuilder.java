package model;

/**
 * BUILDER (design pattern): assembles a Student step by step.
 *
 * @author HE176322
 */
public class StudentBuilder {

    // The student being assembled; handed out by build().
    private Student student;

    // Starts a new, empty student.
    public StudentBuilder() {
        student = new Student();
    }

    // Sets the name, then hands the builder back for the next step.
    public StudentBuilder setName(String name) {
        student.setName(name);
        return this;
    }

    // Sets the class name, then hands the builder back for the next step.
    public StudentBuilder setClasses(String classes) {
        student.setClasses(classes);
        return this;
    }

    // Sets the maths mark, then hands the builder back for the next step.
    public StudentBuilder setMaths(double maths) {
        student.setMaths(maths);
        return this;
    }

    // Sets the chemistry mark, then hands the builder back for the next step.
    public StudentBuilder setChemistry(double chemistry) {
        student.setChemistry(chemistry);
        return this;
    }

    // Sets the physics mark, then hands the builder back for the next step.
    public StudentBuilder setPhysics(double physics) {
        student.setPhysics(physics);
        return this;
    }

    // Finishes the student.
    public Student build() {
        return student;
    }
}
